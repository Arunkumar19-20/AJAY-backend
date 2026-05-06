package com.pmajay.repository;

import com.pmajay.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByStatus(Approval.ApprovalStatus status);
    List<Approval> findBySubmittedBy(Long userId);
    List<Approval> findByAssignedState(Long stateId);
    List<Approval> findByStatusOrderBySubmittedAtDesc(Approval.ApprovalStatus status);
    List<Approval> findAllByOrderBySubmittedAtDesc();
}
