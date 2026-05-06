package com.pmajay.service;

import com.pmajay.model.Document;
import com.pmajay.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public List<Document> getAll() {
        return documentRepository.findAllByOrderByUploadedAtDesc();
    }

    public List<Document> getByAgency(Long agencyId) {
        return documentRepository.findByAgencyIdOrderByUploadedAtDesc(agencyId);
    }

    public List<Document> getByProject(Long projectId) {
        return documentRepository.findByProjectId(projectId);
    }

    public List<Document> getByType(Document.FileType type) {
        return documentRepository.findByFileType(type);
    }

    public Document create(Document document) {
        return documentRepository.save(document);
    }

    public Document verify(Long id) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found: " + id));
        doc.setStatus(Document.DocStatus.VERIFIED);
        return documentRepository.save(doc);
    }
}
