package com.pmajay.controller;

import com.pmajay.model.Approval;
import com.pmajay.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @GetMapping
    public ResponseEntity<List<Approval>> getAll() {
        return ResponseEntity.ok(approvalService.getAll());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Approval>> getByStatus(@PathVariable Approval.ApprovalStatus status) {
        return ResponseEntity.ok(approvalService.getByStatus(status));
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<Approval>> getByState(@PathVariable Long stateId) {
        return ResponseEntity.ok(approvalService.getByState(stateId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Approval>> getBySubmitter(@PathVariable Long userId) {
        return ResponseEntity.ok(approvalService.getBySubmitter(userId));
    }

    @PostMapping
    public ResponseEntity<Approval> create(@RequestBody Approval approval) {
        return ResponseEntity.ok(approvalService.create(approval));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Approval> approve(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(approvalService.approve(id, body.getOrDefault("reviewerId", 0L)));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Approval> reject(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(approvalService.reject(id, body.getOrDefault("reviewerId", 0L)));
    }
}
