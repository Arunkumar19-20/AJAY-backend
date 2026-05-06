package com.pmajay.service;

import com.pmajay.model.Report;
import com.pmajay.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAll() {
        return reportRepository.findAllByOrderBySubmittedAtDesc();
    }

    public List<Report> getByState(Long stateId) {
        return reportRepository.findByStateIdOrderBySubmittedAtDesc(stateId);
    }

    public List<Report> getByType(Report.ReportType type) {
        return reportRepository.findByReportType(type);
    }

    public Report create(Report report) {
        return reportRepository.save(report);
    }

    public Report verify(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));
        report.setStatus(Report.ReportStatus.VERIFIED);
        return reportRepository.save(report);
    }
}
