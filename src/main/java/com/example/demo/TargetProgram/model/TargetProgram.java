package com.example.demo.TargetProgram.model;

import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TargetPrograms")
@Getter
@Setter
public class TargetProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARGETPROGRAM_ID", nullable = false)
    private Integer targetProgramId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "PROGRAM_TYPE")
    private Integer programType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private TargetProgram parent;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private Employee modifiedBy;

    public TargetProgram() {}

    public TargetProgram(Integer targetProgramId, String code, String name, Integer programType,
                         TargetProgram parent, String description, Boolean isActive,
                         LocalDateTime createdDate, LocalDateTime modifiedDate,
                         Employee createdBy, Employee modifiedBy) {
        this.targetProgramId = targetProgramId;
        this.code = code;
        this.name = name;
        this.programType = programType;
        this.parent = parent;
        this.description = description;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }
}
