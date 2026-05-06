package com.pmajay.controller;

import com.pmajay.dto.DashboardSummary;
import com.pmajay.dto.FundSummary;
import com.pmajay.service.DashboardService;
import com.pmajay.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private FundService fundService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummary> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/fund-flow")
    public ResponseEntity<List<FundSummary>> getFundFlow() {
        return ResponseEntity.ok(fundService.getFundSummary());
    }

    @GetMapping("/project-stats")
    public ResponseEntity<DashboardSummary> getProjectStats() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
}
