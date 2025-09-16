package com.example.demo.BudgetType.model;

import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BudgetTypes")
@Getter
@Setter
public class BudgetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUDGET_TYPE_ID", nullable = false)
    private Integer budgetTypeId;

    @Column(name = "BUDGET_TYPE_CODE", nullable = false, length = 50)
    private String budgetTypeCode;

    @Column(name = "BUDGET_TYPE_NAME", nullable = false, length = 255)
    private String budgetTypeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUDGET_TYPE_PARENT_ID")
    private BudgetType budgetTypeParent;

    @Column(name = "IS_USE", nullable = false)
    private Boolean isUse;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_BY")
    private Employee createBy;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private Employee modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    public BudgetType() {}

    public BudgetType(Integer budgetTypeId, String budgetTypeCode, String budgetTypeName, BudgetType budgetTypeParent,
                      Boolean isUse, Boolean isActive, Employee createBy, LocalDateTime createDate,
                      Employee modifiedBy, LocalDateTime modifiedDate, String description) {
        this.budgetTypeId = budgetTypeId;
        this.budgetTypeCode = budgetTypeCode;
        this.budgetTypeName = budgetTypeName;
        this.budgetTypeParent = budgetTypeParent;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}
