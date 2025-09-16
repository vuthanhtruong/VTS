package com.example.demo.Project.model;

import com.example.demo.BudgetType.model.BudgetType;
import com.example.demo.Employee.model.Employee;
import com.example.demo.TargetProgram.model.TargetProgram;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Projects")
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID", nullable = false)
    private Integer projectId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 900)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Project parentProject;

    @Column(name = "PERIOD")
    private Integer period;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "PROJECT_GROUP")
    private Integer projectGroup;

    @Column(name = "PROJEC_TTYPE")
    private Integer projectType;

    @Column(name = "INVESTMENT_GRADE")
    private Integer investmentGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUDGETKINDITEM_ID")
    private BudgetType budgetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGETPROGRAM_ID")
    private TargetProgram targetProgram;

    @Column(name = "INOR", length = 255)
    private String inor;

    @Column(name = "STARTDATE")
    private LocalDateTime startDate;

    @Column(name = "FINISHDATE")
    private LocalDateTime finishDate;

    @Column(name = "CONSTRUCTION_LOCATION", length = 255)
    private String constructionLocation;

    @Column(name = "DESIGNCAPACITY", length = 255)
    private String designCapacity;

    @Column(name = "DISTRICT_TYPE")
    private Integer districtType;

    @Column(name = "IS_CRITERIA", nullable = false)
    private Boolean isCriteria;

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

    public Project() {}

    public Project(Integer projectId, String code, String name, Project parentProject, Integer period,
                   Boolean isActive, Integer projectGroup, Integer projectType, Integer investmentGrade,
                   BudgetType budgetType, TargetProgram targetProgram, String inor,
                   LocalDateTime startDate, LocalDateTime finishDate, String constructionLocation,
                   String designCapacity, Integer districtType, Boolean isCriteria,
                   LocalDateTime createdDate, Employee createdBy,
                   LocalDateTime modifiedDate, Employee modifiedBy) {
        this.projectId = projectId;
        this.code = code;
        this.name = name;
        this.parentProject = parentProject;
        this.period = period;
        this.isActive = isActive;
        this.projectGroup = projectGroup;
        this.projectType = projectType;
        this.investmentGrade = investmentGrade;
        this.budgetType = budgetType;
        this.targetProgram = targetProgram;
        this.inor = inor;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.constructionLocation = constructionLocation;
        this.designCapacity = designCapacity;
        this.districtType = districtType;
        this.isCriteria = isCriteria;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
    }
}
