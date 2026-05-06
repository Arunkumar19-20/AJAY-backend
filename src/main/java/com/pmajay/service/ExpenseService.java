package com.pmajay.service;

import com.pmajay.model.Expense;
import com.pmajay.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAll() {
        return expenseRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Expense> getByAgency(Long agencyId) {
        return expenseRepository.findByAgencyIdOrderByCreatedAtDesc(agencyId);
    }

    public List<Expense> getByProject(Long projectId) {
        return expenseRepository.findByProjectId(projectId);
    }

    public Expense create(Expense expense) {
        return expenseRepository.save(expense);
    }

    public BigDecimal getTotalByProject(Long projectId) {
        return expenseRepository.getTotalByProject(projectId);
    }

    public BigDecimal getTotalByAgency(Long agencyId) {
        return expenseRepository.getTotalByAgency(agencyId);
    }
}
