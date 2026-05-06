package com.pmajay.repository;

import com.pmajay.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStateId(Long stateId);
    List<Report> findByReportType(Report.ReportType type);
    List<Report> findByStatus(Report.ReportStatus status);
    List<Report> findAllByOrderBySubmittedAtDesc();
    List<Report> findByStateIdOrderBySubmittedAtDesc(Long stateId);
}
