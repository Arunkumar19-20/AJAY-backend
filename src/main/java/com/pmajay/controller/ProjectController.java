package com.pmajay.controller;

import com.pmajay.model.Project;
import com.pmajay.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return ResponseEntity.ok(projectService.updateProject(id, project));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable Project.Status status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    @GetMapping("/delayed")
    public ResponseEntity<List<Project>> getDelayedProjects() {
        return ResponseEntity.ok(projectService.getDelayedProjects());
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Project> updateProgress(@PathVariable Long id, @RequestBody java.util.Map<String, Integer> body) {
        return ResponseEntity.ok(projectService.updateProgress(id, body.getOrDefault("progress", 0)));
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<Project>> getByState(@PathVariable Long stateId) {
        return ResponseEntity.ok(projectService.getProjectsByState(stateId));
    }

    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<List<Project>> getByAgency(@PathVariable Long agencyId) {
        return ResponseEntity.ok(projectService.getProjectsByAgency(agencyId));
    }
}
