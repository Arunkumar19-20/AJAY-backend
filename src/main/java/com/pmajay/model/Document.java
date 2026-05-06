package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", columnDefinition = "ENUM('PHOTO','BILL','VOUCHER','INSPECTION')")
    private FileType fileType;

    @Column(name = "file_size", length = 50)
    private String fileSize;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "agency_id")
    private Long agencyId;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "file_data", columnDefinition = "LONGTEXT")
    private String fileData;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING','VERIFIED')")
    private DocStatus status;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
        if (status == null) status = DocStatus.PENDING;
    }

    public enum FileType {
        PHOTO, BILL, VOUCHER, INSPECTION
    }

    public enum DocStatus {
        PENDING, VERIFIED
    }
}
