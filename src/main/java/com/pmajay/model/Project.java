package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADARSH_GRAM','GIA','HOSTEL')")
    private Agency.Component component;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "agency_id")
    private Long agencyId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING','IN_PROGRESS','COMPLETED','DELAYED')")
    private Status status;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "assigned_officer", length = 100)
    private String assignedOfficer;

    @Column(name = "progress")
    private Integer progress = 0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED, DELAYED
    }
}
