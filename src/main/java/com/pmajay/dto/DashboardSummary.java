package com.pmajay.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummary {
    private long totalProjects;
    private long pendingProjects;
    private long inProgressProjects;
    private long completedProjects;
    private long delayedProjects;
    private long totalAgencies;
    private BigDecimal totalFundsReleased;
    private BigDecimal totalFundsUtilized;
    private Map<String, Long> projectsByComponent;
    private Map<String, Long> projectsByStatus;
}
