package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "approvals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 15, scale = 2)
    private BigDecimal budget;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADARSH_GRAM','GIA','HOSTEL')")
    private Agency.Component component;

    @Column(name = "submitted_by")
    private Long submittedBy;

    @Column(name = "assigned_state")
    private Long assignedState;

    @Column(name = "agency_name", length = 150)
    private String agencyName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('DPR','WORK_PLAN')")
    private ApprovalType type;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING_STATE','PENDING_CENTRE','APPROVED','REJECTED')")
    private ApprovalStatus status;

    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "reviewed_by")
    private Long reviewedBy;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        if (status == null)
            status = ApprovalStatus.PENDING_STATE;
    }

    public enum ApprovalType {
        DPR, WORK_PLAN
    }

    public enum ApprovalStatus {
        PENDING_STATE, PENDING_CENTRE, APPROVED, REJECTED, PENDING
    }
}
