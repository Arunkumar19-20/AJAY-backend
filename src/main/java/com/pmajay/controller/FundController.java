package com.pmajay.controller;

import com.pmajay.dto.FundSummary;
import com.pmajay.model.FundTransaction;
import com.pmajay.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funds")
public class FundController {

    @Autowired
    private FundService fundService;

    @GetMapping
    public ResponseEntity<List<FundTransaction>> getAllFunds() {
        return ResponseEntity.ok(fundService.getAllFunds());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<FundTransaction>> getFundsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(fundService.getFundsByProject(projectId));
    }

    @PostMapping
    public ResponseEntity<FundTransaction> createFund(@RequestBody FundTransaction fund) {
        return ResponseEntity.ok(fundService.createFundTransaction(fund));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundTransaction> updateFund(@PathVariable Long id, @RequestBody FundTransaction fund) {
        return ResponseEntity.ok(fundService.updateFundTransaction(id, fund));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<FundSummary>> getFundSummary() {
        return ResponseEntity.ok(fundService.getFundSummary());
    }
}
