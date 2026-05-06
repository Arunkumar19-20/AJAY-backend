package com.pmajay.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String item;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('MATERIAL','LABOUR','EQUIPMENT','TRANSPORT','OTHER')")
    private Category category;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "agency_id")
    private Long agencyId;

    @Column(name = "voucher_no", length = 50)
    private String voucherNo;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum Category {
        MATERIAL, LABOUR, EQUIPMENT, TRANSPORT, OTHER
    }
}
