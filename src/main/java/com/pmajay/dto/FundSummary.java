package com.pmajay.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundSummary {
    private String stateName;
    private Long stateId;
    private String component;
    private BigDecimal totalReleased;
    private BigDecimal totalUtilized;
    private BigDecimal pendingUtilization;
    private long transactionCount;
}
