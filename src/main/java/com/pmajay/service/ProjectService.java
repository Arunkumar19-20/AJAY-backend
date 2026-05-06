package com.pmajay.service;

import com.pmajay.model.Project;
import com.pmajay.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = getProjectById(id);
        project.setTitle(projectDetails.getTitle());
        project.setComponent(projectDetails.getComponent());
        project.setStateId(projectDetails.getStateId());
        project.setAgencyId(projectDetails.getAgencyId());
        project.setStatus(projectDetails.getStatus());
        project.setStartDate(projectDetails.getStartDate());
        project.setDeadline(projectDetails.getDeadline());
        project.setAssignedOfficer(projectDetails.getAssignedOfficer());
        return projectRepository.save(project);
    }

    public List<Project> getProjectsByStatus(Project.Status status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> getDelayedProjects() {
        return projectRepository.findDelayedProjects(LocalDate.now());
    }

    public List<Project> getProjectsByState(Long stateId) {
        return projectRepository.findByStateId(stateId);
    }

    public List<Project> getProjectsByAgency(Long agencyId) {
        return projectRepository.findByAgencyId(agencyId);
    }

    public Project updateProgress(Long id, Integer progress) {
        Project project = getProjectById(id);
        project.setProgress(Math.min(100, Math.max(0, progress)));
        if (progress >= 100) {
            project.setStatus(Project.Status.COMPLETED);
        } else if (progress > 0) {
            project.setStatus(Project.Status.IN_PROGRESS);
        }
        return projectRepository.save(project);
    }
}
