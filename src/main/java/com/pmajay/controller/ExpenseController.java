package com.pmajay.controller;

import com.pmajay.model.Expense;
import com.pmajay.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.ok(expenseService.getAll());
    }

    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<List<Expense>> getByAgency(@PathVariable Long agencyId) {
        return ResponseEntity.ok(expenseService.getByAgency(agencyId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Expense>> getByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(expenseService.getByProject(projectId));
    }

    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.create(expense));
    }

    @GetMapping("/total/project/{projectId}")
    public ResponseEntity<BigDecimal> getTotalByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(expenseService.getTotalByProject(projectId));
    }

    @GetMapping("/total/agency/{agencyId}")
    public ResponseEntity<BigDecimal> getTotalByAgency(@PathVariable Long agencyId) {
        return ResponseEntity.ok(expenseService.getTotalByAgency(agencyId));
    }
}
