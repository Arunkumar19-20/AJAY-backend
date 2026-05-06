package com.pmajay.service;

import com.pmajay.dto.FundSummary;
import com.pmajay.model.FundTransaction;
import com.pmajay.model.Project;
import com.pmajay.model.State;
import com.pmajay.repository.FundTransactionRepository;
import com.pmajay.repository.ProjectRepository;
import com.pmajay.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FundService {

    @Autowired
    private FundTransactionRepository fundTransactionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StateRepository stateRepository;

    public List<FundTransaction> getAllFunds() {
        return fundTransactionRepository.findAll();
    }

    public List<FundTransaction> getFundsByProject(Long projectId) {
        return fundTransactionRepository.findByProjectId(projectId);
    }

    public FundTransaction createFundTransaction(FundTransaction fund) {
        return fundTransactionRepository.save(fund);
    }

    public FundTransaction updateFundTransaction(Long id, FundTransaction fundDetails) {
        FundTransaction fund = fundTransactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fund transaction not found with id: " + id));
        fund.setAmount(fundDetails.getAmount());
        fund.setReleasedBy(fundDetails.getReleasedBy());
        fund.setReleaseDate(fundDetails.getReleaseDate());
        fund.setReceivedDate(fundDetails.getReceivedDate());
        fund.setUtilizationStatus(fundDetails.getUtilizationStatus());
        fund.setRemarks(fundDetails.getRemarks());
        return fundTransactionRepository.save(fund);
    }

    public List<FundSummary> getFundSummary() {
        List<FundTransaction> allFunds = fundTransactionRepository.findAll();
        List<State> allStates = stateRepository.findAll();
        Map<Long, String> stateNames = allStates.stream()
                .collect(Collectors.toMap(State::getId, State::getName));

        // Group by project -> state
        Map<Long, List<FundTransaction>> byProject = allFunds.stream()
                .collect(Collectors.groupingBy(FundTransaction::getProjectId));

        Map<Long, BigDecimal> releasedByState = new HashMap<>();
        Map<Long, BigDecimal> utilizedByState = new HashMap<>();
        Map<Long, Long> countByState = new HashMap<>();

        for (Map.Entry<Long, List<FundTransaction>> entry : byProject.entrySet()) {
            Project project = projectRepository.findById(entry.getKey()).orElse(null);
            if (project == null) continue;

            Long stateId = project.getStateId();
            for (FundTransaction ft : entry.getValue()) {
                releasedByState.merge(stateId, ft.getAmount() != null ? ft.getAmount() : BigDecimal.ZERO, BigDecimal::add);
                if (ft.getUtilizationStatus() == FundTransaction.UtilizationStatus.FULL) {
                    utilizedByState.merge(stateId, ft.getAmount() != null ? ft.getAmount() : BigDecimal.ZERO, BigDecimal::add);
                }
                countByState.merge(stateId, 1L, Long::sum);
            }
        }

        List<FundSummary> summaries = new ArrayList<>();
        for (Long stateId : releasedByState.keySet()) {
            BigDecimal released = releasedByState.getOrDefault(stateId, BigDecimal.ZERO);
            BigDecimal utilized = utilizedByState.getOrDefault(stateId, BigDecimal.ZERO);
            summaries.add(FundSummary.builder()
                    .stateId(stateId)
                    .stateName(stateNames.getOrDefault(stateId, "Unknown"))
                    .totalReleased(released)
                    .totalUtilized(utilized)
                    .pendingUtilization(released.subtract(utilized))
                    .transactionCount(countByState.getOrDefault(stateId, 0L))
                    .build());
        }
        return summaries;
    }
}
