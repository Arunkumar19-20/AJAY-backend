package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fund_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "released_by", columnDefinition = "ENUM('CENTRE','STATE')")
    private User.Role releasedBy;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "utilization_status", columnDefinition = "ENUM('PENDING','PARTIAL','FULL')")
    private UtilizationStatus utilizationStatus;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    public enum UtilizationStatus {
        PENDING, PARTIAL, FULL
    }
}
