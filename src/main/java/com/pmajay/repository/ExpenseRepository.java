package com.pmajay.repository;

import com.pmajay.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByAgencyId(Long agencyId);
    List<Expense> findByProjectId(Long projectId);
    List<Expense> findByAgencyIdOrderByCreatedAtDesc(Long agencyId);
    List<Expense> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.projectId = :projectId")
    BigDecimal getTotalByProject(Long projectId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.agencyId = :agencyId")
    BigDecimal getTotalByAgency(Long agencyId);
}
