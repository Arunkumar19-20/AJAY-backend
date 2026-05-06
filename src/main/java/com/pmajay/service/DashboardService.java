package com.pmajay.service;

import com.pmajay.dto.DashboardSummary;
import com.pmajay.model.Agency;
import com.pmajay.model.Project;
import com.pmajay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private FundTransactionRepository fundTransactionRepository;

    public DashboardSummary getSummary() {
        Map<String, Long> byStatus = new HashMap<>();
        byStatus.put("PENDING", projectRepository.countByStatus(Project.Status.PENDING));
        byStatus.put("IN_PROGRESS", projectRepository.countByStatus(Project.Status.IN_PROGRESS));
        byStatus.put("COMPLETED", projectRepository.countByStatus(Project.Status.COMPLETED));
        byStatus.put("DELAYED", projectRepository.countByStatus(Project.Status.DELAYED));

        Map<String, Long> byComponent = new HashMap<>();
        byComponent.put("ADARSH_GRAM", projectRepository.countByComponent(Agency.Component.ADARSH_GRAM));
        byComponent.put("GIA", projectRepository.countByComponent(Agency.Component.GIA));
        byComponent.put("HOSTEL", projectRepository.countByComponent(Agency.Component.HOSTEL));

        BigDecimal totalReleased = fundTransactionRepository.getTotalReleased();
        BigDecimal totalUtilized = fundTransactionRepository.getTotalUtilized();

        return DashboardSummary.builder()
                .totalProjects(projectRepository.count())
                .pendingProjects(byStatus.getOrDefault("PENDING", 0L))
                .inProgressProjects(byStatus.getOrDefault("IN_PROGRESS", 0L))
                .completedProjects(byStatus.getOrDefault("COMPLETED", 0L))
                .delayedProjects(byStatus.getOrDefault("DELAYED", 0L))
                .totalAgencies(agencyRepository.count())
                .totalFundsReleased(totalReleased)
                .totalFundsUtilized(totalUtilized)
                .projectsByStatus(byStatus)
                .projectsByComponent(byComponent)
                .build();
    }
}
