package com.pmajay.controller;

import com.pmajay.model.Report;
import com.pmajay.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<List<Report>> getAll() {
        return ResponseEntity.ok(reportService.getAll());
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<Report>> getByState(@PathVariable Long stateId) {
        return ResponseEntity.ok(reportService.getByState(stateId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Report>> getByType(@PathVariable Report.ReportType type) {
        return ResponseEntity.ok(reportService.getByType(type));
    }

    @PostMapping
    public ResponseEntity<Report> create(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.create(report));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Report> verify(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.verify(id));
    }
}
