package com.pmajay.config;

import com.pmajay.model.*;
import com.pmajay.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Seeds initial data for development/demo purposes.
 * Creates states, sample users, agencies, projects, and fund transactions.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    @Autowired private StateRepository stateRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AgencyRepository agencyRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private FundTransactionRepository fundTransactionRepository;
    @Autowired private ApprovalRepository approvalRepository;
    @Autowired private DocumentRepository documentRepository;
    @Autowired private ExpenseRepository expenseRepository;
    @Autowired private ReportRepository reportRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        // Migration for old status values
        try {
            // Safely alter ENUM: first add new values alongside old ones
            jdbcTemplate.execute("ALTER TABLE approvals MODIFY COLUMN status ENUM('PENDING', 'PENDING_STATE', 'PENDING_CENTRE', 'APPROVED', 'REJECTED')");
            // Update the rows
            jdbcTemplate.execute("UPDATE approvals SET status = 'PENDING_STATE' WHERE status = 'PENDING'");
            // Then restrict ENUM to only the new values
            jdbcTemplate.execute("ALTER TABLE approvals MODIFY COLUMN status ENUM('PENDING_STATE', 'PENDING_CENTRE', 'APPROVED', 'REJECTED')");
        } catch (Exception e) {
            logger.debug("Migration notice: {}", e.getMessage());
        }

        // Ensure missing agency users are created for testing
        if (userRepository.findByEmail("agency-bihar@pmajay.gov.in").isEmpty() && agencyRepository.count() > 0) {
            logger.info("Seeding additional agency users...");
            List<Agency> agencies = agencyRepository.findAll();
            if (agencies.size() >= 5) {
                userRepository.save(User.builder().name("Agency Officer Bihar").email("agency-bihar@pmajay.gov.in").password(passwordEncoder.encode("agency123")).role(User.Role.AGENCY).stateId(2L).agencyId(agencies.get(1).getId()).build());
                userRepository.save(User.builder().name("Agency Officer Gujarat").email("agency-gujarat@pmajay.gov.in").password(passwordEncoder.encode("agency123")).role(User.Role.AGENCY).stateId(4L).agencyId(agencies.get(2).getId()).build());
                userRepository.save(User.builder().name("Agency Officer Maharashtra").email("agency-mh@pmajay.gov.in").password(passwordEncoder.encode("agency123")).role(User.Role.AGENCY).stateId(8L).agencyId(agencies.get(3).getId()).build());
                userRepository.save(User.builder().name("Agency Officer UP").email("agency-up@pmajay.gov.in").password(passwordEncoder.encode("agency123")).role(User.Role.AGENCY).stateId(13L).agencyId(agencies.get(4).getId()).build());
            }
        }

        if (stateRepository.count() > 0) return; // Already seeded

        // Seed States
        List<State> states = List.of(
            State.builder().name("Andhra Pradesh").code("AP").build(),
            State.builder().name("Bihar").code("BR").build(),
            State.builder().name("Chhattisgarh").code("CG").build(),
            State.builder().name("Gujarat").code("GJ").build(),
            State.builder().name("Jharkhand").code("JH").build(),
            State.builder().name("Karnataka").code("KA").build(),
            State.builder().name("Madhya Pradesh").code("MP").build(),
            State.builder().name("Maharashtra").code("MH").build(),
            State.builder().name("Odisha").code("OR").build(),
            State.builder().name("Rajasthan").code("RJ").build(),
            State.builder().name("Tamil Nadu").code("TN").build(),
            State.builder().name("Telangana").code("TS").build(),
            State.builder().name("Uttar Pradesh").code("UP").build(),
            State.builder().name("West Bengal").code("WB").build()
        );
        stateRepository.saveAll(states);

        // Seed Users
        userRepository.save(User.builder()
                .name("Admin Centre").email("admin@pmajay.gov.in")
                .password(passwordEncoder.encode("admin123"))
                .role(User.Role.CENTRE).build());

        userRepository.save(User.builder()
                .name("AP State Officer").email("ap@pmajay.gov.in")
                .password(passwordEncoder.encode("state123"))
                .role(User.Role.STATE).stateId(1L).build());

        userRepository.save(User.builder()
                .name("Bihar State Officer").email("bihar@pmajay.gov.in")
                .password(passwordEncoder.encode("state123"))
                .role(User.Role.STATE).stateId(2L).build());

        // Seed Agencies
        Agency a1 = agencyRepository.save(Agency.builder()
                .name("AP Tribal Welfare Agency").type("Government").component(Agency.Component.ADARSH_GRAM)
                .district("Visakhapatnam").stateId(1L).nodalOfficer("Dr. Rajesh Kumar").contact("9876543210").build());

        Agency a2 = agencyRepository.save(Agency.builder()
                .name("Bihar SC/ST Development Corp").type("Corporation").component(Agency.Component.GIA)
                .district("Patna").stateId(2L).nodalOfficer("Shri Manoj Singh").contact("9876543211").build());

        Agency a3 = agencyRepository.save(Agency.builder()
                .name("Gujarat Hostel Board").type("Board").component(Agency.Component.HOSTEL)
                .district("Ahmedabad").stateId(4L).nodalOfficer("Smt. Priya Patel").contact("9876543212").build());

        Agency a4 = agencyRepository.save(Agency.builder()
                .name("Maharashtra Tribal Dev. Dept").type("Government").component(Agency.Component.ADARSH_GRAM)
                .district("Mumbai").stateId(8L).nodalOfficer("Shri Amit Deshmukh").contact("9876543213").build());

        Agency a5 = agencyRepository.save(Agency.builder()
                .name("UP Welfare Society").type("Society").component(Agency.Component.GIA)
                .district("Lucknow").stateId(13L).nodalOfficer("Shri Vikash Yadav").contact("9876543214").build());

        // Agency users (created after agencies so we can link agencyId)
        userRepository.save(User.builder()
                .name("Agency Officer AP").email("agency-ap@pmajay.gov.in")
                .password(passwordEncoder.encode("agency123"))
                .role(User.Role.AGENCY).stateId(1L).agencyId(a1.getId()).build());

        userRepository.save(User.builder()
                .name("Agency Officer Bihar").email("agency-bihar@pmajay.gov.in")
                .password(passwordEncoder.encode("agency123"))
                .role(User.Role.AGENCY).stateId(2L).agencyId(a2.getId()).build());

        userRepository.save(User.builder()
                .name("Agency Officer Gujarat").email("agency-gujarat@pmajay.gov.in")
                .password(passwordEncoder.encode("agency123"))
                .role(User.Role.AGENCY).stateId(4L).agencyId(a3.getId()).build());

        userRepository.save(User.builder()
                .name("Agency Officer Maharashtra").email("agency-mh@pmajay.gov.in")
                .password(passwordEncoder.encode("agency123"))
                .role(User.Role.AGENCY).stateId(8L).agencyId(a4.getId()).build());

        userRepository.save(User.builder()
                .name("Agency Officer UP").email("agency-up@pmajay.gov.in")
                .password(passwordEncoder.encode("agency123"))
                .role(User.Role.AGENCY).stateId(13L).agencyId(a5.getId()).build());

        // Seed Projects
        Project p1 = projectRepository.save(Project.builder()
                .title("Adarsh Gram Development - Visakhapatnam Phase 1").component(Agency.Component.ADARSH_GRAM)
                .stateId(1L).agencyId(a1.getId()).status(Project.Status.IN_PROGRESS)
                .startDate(LocalDate.of(2025, 4, 1)).deadline(LocalDate.of(2026, 3, 31))
                .assignedOfficer("Dr. Rajesh Kumar").build());

        Project p2 = projectRepository.save(Project.builder()
                .title("GIA Grant for Patna District Schools").component(Agency.Component.GIA)
                .stateId(2L).agencyId(a2.getId()).status(Project.Status.PENDING)
                .startDate(LocalDate.of(2025, 6, 1)).deadline(LocalDate.of(2026, 5, 31))
                .assignedOfficer("Shri Manoj Singh").build());

        Project p3 = projectRepository.save(Project.builder()
                .title("SC/ST Hostel Construction - Ahmedabad").component(Agency.Component.HOSTEL)
                .stateId(4L).agencyId(a3.getId()).status(Project.Status.COMPLETED)
                .startDate(LocalDate.of(2024, 1, 1)).deadline(LocalDate.of(2025, 12, 31))
                .assignedOfficer("Smt. Priya Patel").build());

        Project p4 = projectRepository.save(Project.builder()
                .title("Adarsh Gram Development - Raigad District").component(Agency.Component.ADARSH_GRAM)
                .stateId(8L).agencyId(a4.getId()).status(Project.Status.DELAYED)
                .startDate(LocalDate.of(2024, 7, 1)).deadline(LocalDate.of(2025, 6, 30))
                .assignedOfficer("Shri Amit Deshmukh").build());

        Project p5 = projectRepository.save(Project.builder()
                .title("GIA Educational Support - Lucknow").component(Agency.Component.GIA)
                .stateId(13L).agencyId(a5.getId()).status(Project.Status.IN_PROGRESS)
                .startDate(LocalDate.of(2025, 1, 15)).deadline(LocalDate.of(2026, 1, 14))
                .assignedOfficer("Shri Vikash Yadav").build());

        Project p6 = projectRepository.save(Project.builder()
                .title("Hostel Renovation - Visakhapatnam").component(Agency.Component.HOSTEL)
                .stateId(1L).agencyId(a1.getId()).status(Project.Status.PENDING)
                .startDate(LocalDate.of(2025, 8, 1)).deadline(LocalDate.of(2026, 7, 31))
                .assignedOfficer("Dr. Rajesh Kumar").build());

        // Seed Fund Transactions
        fundTransactionRepository.save(FundTransaction.builder()
                .projectId(p1.getId()).amount(new BigDecimal("5000000.00")).releasedBy(User.Role.CENTRE)
                .releaseDate(LocalDate.of(2025, 4, 15)).receivedDate(LocalDate.of(2025, 4, 25))
                .utilizationStatus(FundTransaction.UtilizationStatus.PARTIAL).remarks("First installment released").build());

        fundTransactionRepository.save(FundTransaction.builder()
                .projectId(p1.getId()).amount(new BigDecimal("3000000.00")).releasedBy(User.Role.STATE)
                .releaseDate(LocalDate.of(2025, 7, 1)).receivedDate(LocalDate.of(2025, 7, 10))
                .utilizationStatus(FundTransaction.UtilizationStatus.FULL).remarks("State matching fund").build());

        fundTransactionRepository.save(FundTransaction.builder()
                .projectId(p2.getId()).amount(new BigDecimal("2500000.00")).releasedBy(User.Role.CENTRE)
                .releaseDate(LocalDate.of(2025, 6, 15))
                .utilizationStatus(FundTransaction.UtilizationStatus.PENDING).remarks("Pending agency receipt").build());

        fundTransactionRepository.save(FundTransaction.builder()
                .projectId(p3.getId()).amount(new BigDecimal("8000000.00")).releasedBy(User.Role.CENTRE)
                .releaseDate(LocalDate.of(2024, 2, 1)).receivedDate(LocalDate.of(2024, 2, 15))
                .utilizationStatus(FundTransaction.UtilizationStatus.FULL).remarks("Hostel construction completed").build());

        fundTransactionRepository.save(FundTransaction.builder()
                .projectId(p4.getId()).amount(new BigDecimal("4500000.00")).releasedBy(User.Role.CENTRE)
                .releaseDate(LocalDate.of(2024, 8, 1)).receivedDate(LocalDate.of(2024, 8, 20))
                .utilizationStatus(FundTransaction.UtilizationStatus.PARTIAL).remarks("Project delayed, partial utilization").build());

        fundTransactionRepository.save(FundTransaction.builder()
                .projectId(p5.getId()).amount(new BigDecimal("1500000.00")).releasedBy(User.Role.CENTRE)
                .releaseDate(LocalDate.of(2025, 2, 1)).receivedDate(LocalDate.of(2025, 2, 10))
                .utilizationStatus(FundTransaction.UtilizationStatus.PARTIAL).remarks("Initial grant disbursed").build());

        // Seed Approvals (DPRs)
        approvalRepository.save(Approval.builder()
                .title("Hostel Block-C Construction DPR").description("Detailed project report for girls hostel block C")
                .budget(new BigDecimal("5200000")).component(Agency.Component.HOSTEL).type(Approval.ApprovalType.DPR)
                .submittedBy(4L).assignedState(1L).agencyName("AP Tribal Welfare Agency").status(Approval.ApprovalStatus.PENDING_STATE).build());

        approvalRepository.save(Approval.builder()
                .title("Village Road & Drainage Work Plan").description("Infrastructure development work plan")
                .budget(new BigDecimal("2800000")).component(Agency.Component.ADARSH_GRAM).type(Approval.ApprovalType.WORK_PLAN)
                .submittedBy(4L).assignedState(1L).agencyName("AP Tribal Welfare Agency").status(Approval.ApprovalStatus.PENDING_STATE).build());

        approvalRepository.save(Approval.builder()
                .title("GIA Stipend Distribution Plan").description("Quarterly stipend distribution plan")
                .budget(new BigDecimal("1800000")).component(Agency.Component.GIA).type(Approval.ApprovalType.DPR)
                .submittedBy(4L).assignedState(2L).agencyName("Bihar SC/ST Development Corp").status(Approval.ApprovalStatus.APPROVED).build());

        // Seed Documents
        documentRepository.save(Document.builder()
                .fileName("site_progress_photo_dec.jpg").fileType(Document.FileType.PHOTO).fileSize("3.2 MB")
                .projectId(p1.getId()).agencyId(a1.getId()).uploadedBy(4L)
                .description("Progress photo of site work").status(Document.DocStatus.VERIFIED).build());

        documentRepository.save(Document.builder()
                .fileName("material_bill_cement.pdf").fileType(Document.FileType.BILL).fileSize("1.1 MB")
                .projectId(p1.getId()).agencyId(a1.getId()).uploadedBy(4L)
                .description("Cement purchase bill").status(Document.DocStatus.PENDING).build());

        documentRepository.save(Document.builder()
                .fileName("inspection_report_nov.pdf").fileType(Document.FileType.INSPECTION).fileSize("2.8 MB")
                .projectId(p1.getId()).agencyId(a1.getId()).uploadedBy(4L)
                .description("November inspection report").status(Document.DocStatus.PENDING).build());

        // Seed Expenses
        expenseRepository.save(Expense.builder()
                .item("Cement & Sand").amount(new BigDecimal("245000")).category(Expense.Category.MATERIAL)
                .projectId(p1.getId()).agencyId(a1.getId()).voucherNo("V-1082")
                .expenseDate(LocalDate.of(2025, 12, 8)).createdBy(4L).build());

        expenseRepository.save(Expense.builder()
                .item("Labour Wages - Dec Week 1").amount(new BigDecimal("180000")).category(Expense.Category.LABOUR)
                .projectId(p1.getId()).agencyId(a1.getId()).voucherNo("V-1081")
                .expenseDate(LocalDate.of(2025, 12, 5)).createdBy(4L).build());

        expenseRepository.save(Expense.builder()
                .item("Steel TMT Bars").amount(new BigDecimal("312000")).category(Expense.Category.MATERIAL)
                .projectId(p1.getId()).agencyId(a1.getId()).voucherNo("V-1080")
                .expenseDate(LocalDate.of(2025, 11, 28)).createdBy(4L).build());

        // Seed Reports
        reportRepository.save(Report.builder()
                .title("Q3 2025 Utilization Certificate").reportType(Report.ReportType.UC).quarter("Q3")
                .amount(new BigDecimal("18000000")).stateId(1L).submittedBy(2L)
                .remarks("Funds utilized for Adarsh Gram projects").status(Report.ReportStatus.SUBMITTED).build());

        reportRepository.save(Report.builder()
                .title("Quarterly Progress Report - Q3").reportType(Report.ReportType.QPR).quarter("Q3")
                .amount(new BigDecimal("9200000")).stateId(2L).submittedBy(3L)
                .remarks("Bihar state quarterly progress").status(Report.ReportStatus.VERIFIED).build());

        reportRepository.save(Report.builder()
                .title("Annual Performance Report 2024-25").reportType(Report.ReportType.APR).quarter("")
                .amount(new BigDecimal("45000000")).stateId(1L).submittedBy(2L)
                .remarks("Annual consolidated report").status(Report.ReportStatus.SUBMITTED).build());

        logger.info("Sample data seeded successfully.");
    }
}
