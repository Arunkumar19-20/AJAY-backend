package com.pmajay.repository;

import com.pmajay.model.Agency;
import com.pmajay.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(Project.Status status);
    List<Project> findByStateId(Long stateId);
    List<Project> findByAgencyId(Long agencyId);
    List<Project> findByComponent(Agency.Component component);

    @Query("SELECT p FROM Project p WHERE p.deadline < :today AND p.status != 'COMPLETED'")
    List<Project> findDelayedProjects(LocalDate today);

    long countByStatus(Project.Status status);
    long countByComponent(Agency.Component component);

    @Query("SELECT p FROM Project p WHERE p.deadline < :today AND p.status NOT IN ('COMPLETED','DELAYED')")
    List<Project> findProjectsPastDeadline(LocalDate today);
}
