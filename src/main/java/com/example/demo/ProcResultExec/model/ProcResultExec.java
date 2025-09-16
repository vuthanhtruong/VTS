package com.example.demo.ProcResultExec.model;

import com.example.demo.Project.model.Project;
import com.example.demo.Voucher.model.Voucher;
import com.example.demo.Unit.model.Unit;
import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProcResultExec")
@Getter
@Setter
public class ProcResultExec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROC_RESULT_EXEC_ID", nullable = false)
    private Integer procResultExecId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    private Project project;

    @Column(name = "DECISION_NO", nullable = false, length = 50)
    private String decisionNo;

    @Column(name = "DECISION_DATE", nullable = false)
    private LocalDateTime decisionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOUCHER_ID")
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private Employee createdBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private Employee modifiedBy;

    public ProcResultExec() {}

    public ProcResultExec(Integer procResultExecId, Project project, String decisionNo, LocalDateTime decisionDate,
                          Voucher voucher, Unit unit, LocalDateTime createdDate, Employee createdBy,
                          LocalDateTime modifiedDate, Employee modifiedBy) {
        this.procResultExecId = procResultExecId;
        this.project = project;
        this.decisionNo = decisionNo;
        this.decisionDate = decisionDate;
        this.voucher = voucher;
        this.unit = unit;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
    }
}
