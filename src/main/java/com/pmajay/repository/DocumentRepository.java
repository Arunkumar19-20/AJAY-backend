package com.pmajay.repository;

import com.pmajay.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByAgencyId(Long agencyId);
    List<Document> findByProjectId(Long projectId);
    List<Document> findByFileType(Document.FileType fileType);
    List<Document> findAllByOrderByUploadedAtDesc();
    List<Document> findByAgencyIdOrderByUploadedAtDesc(Long agencyId);
}
