package com.pmajay.service;

import com.pmajay.model.Approval;
import com.pmajay.model.User;
import com.pmajay.repository.ApprovalRepository;
import com.pmajay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Approval> getAll() {
        return approvalRepository.findAllByOrderBySubmittedAtDesc();
    }

    public List<Approval> getByStatus(Approval.ApprovalStatus status) {
        return approvalRepository.findByStatusOrderBySubmittedAtDesc(status);
    }

    public List<Approval> getByState(Long stateId) {
        return approvalRepository.findByAssignedState(stateId);
    }

    public List<Approval> getBySubmitter(Long userId) {
        return approvalRepository.findBySubmittedBy(userId);
    }

    public Approval create(Approval approval) {
        return approvalRepository.save(approval);
    }

    public Approval approve(Long id, Long reviewerId) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Approval not found: " + id));
        
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new RuntimeException("Reviewer not found: " + reviewerId));

        if (reviewer.getRole() == User.Role.STATE) {
            approval.setStatus(Approval.ApprovalStatus.PENDING_CENTRE);
        } else if (reviewer.getRole() == User.Role.CENTRE) {
            approval.setStatus(Approval.ApprovalStatus.APPROVED);
        }

        approval.setReviewedAt(LocalDateTime.now());
        approval.setReviewedBy(reviewerId);
        return approvalRepository.save(approval);
    }

    public Approval reject(Long id, Long reviewerId) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Approval not found: " + id));
        approval.setStatus(Approval.ApprovalStatus.REJECTED);
        approval.setReviewedAt(LocalDateTime.now());
        approval.setReviewedBy(reviewerId);
        return approvalRepository.save(approval);
    }
}
