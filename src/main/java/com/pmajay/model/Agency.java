package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String name;

    @Column(length = 100)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADARSH_GRAM','GIA','HOSTEL')")
    private Component component;

    @Column(length = 100)
    private String district;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "nodal_officer", length = 100)
    private String nodalOfficer;

    @Column(length = 15)
    private String contact;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Component {
        ADARSH_GRAM, GIA, HOSTEL
    }
}
