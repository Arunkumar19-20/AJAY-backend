package com.pmajay.repository;

import com.pmajay.model.FundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransaction, Long> {
    List<FundTransaction> findByProjectId(Long projectId);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FundTransaction f")
    BigDecimal getTotalReleased();

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FundTransaction f WHERE f.utilizationStatus = 'FULL'")
    BigDecimal getTotalUtilized();
}
