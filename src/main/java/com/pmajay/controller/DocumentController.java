package com.pmajay.controller;

import com.pmajay.model.Document;
import com.pmajay.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<Document>> getAll() {
        return ResponseEntity.ok(documentService.getAll());
    }

    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<List<Document>> getByAgency(@PathVariable Long agencyId) {
        return ResponseEntity.ok(documentService.getByAgency(agencyId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Document>> getByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(documentService.getByProject(projectId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Document>> getByType(@PathVariable Document.FileType type) {
        return ResponseEntity.ok(documentService.getByType(type));
    }

    @PostMapping
    public ResponseEntity<Document> create(@RequestBody Document document) {
        return ResponseEntity.ok(documentService.create(document));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Document> verify(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.verify(id));
    }
}
