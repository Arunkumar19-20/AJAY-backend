package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", columnDefinition = "ENUM('UC','QPR','APR','PPR')")
    private ReportType reportType;

    @Column(length = 10)
    private String quarter;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "submitted_by")
    private Long submittedBy;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('SUBMITTED','VERIFIED')")
    private ReportStatus status;

    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        if (status == null) status = ReportStatus.SUBMITTED;
    }

    public enum ReportType {
        UC, QPR, APR, PPR
    }

    public enum ReportStatus {
        SUBMITTED, VERIFIED
    }
}
