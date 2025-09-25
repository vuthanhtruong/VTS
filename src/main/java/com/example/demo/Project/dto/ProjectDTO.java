package com.example.demo.Project.dto;

import com.example.demo.BudgetType.model.BudgetType;
import com.example.demo.Employee.model.Employee;
import com.example.demo.Project.model.Project;
import com.example.demo.TargetProgram.model.TargetProgram;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {

    private Integer projectId;
    private String code;
    private String name;
    private Integer parentProjectId;
    private Integer period;
    private Boolean isActive;
    private Integer projectGroup;
    private Integer projectType;
    private Integer investmentGrade;
    private Integer budgetTypeId;
    private Integer targetProgramId;
    private String inor;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String constructionLocation;
    private String designCapacity;
    private Integer districtType;
    private Boolean isCriteria;
    private LocalDateTime createdDate;
    private Integer createdById;
    private LocalDateTime modifiedDate;
    private Integer modifiedById;

    public ProjectDTO() {}

    public ProjectDTO(Integer projectId, String code, String name, Integer parentProjectId, Integer period,
                      Boolean isActive, Integer projectGroup, Integer projectType, Integer investmentGrade,
                      Integer budgetTypeId, Integer targetProgramId, String inor,
                      LocalDateTime startDate, LocalDateTime finishDate, String constructionLocation,
                      String designCapacity, Integer districtType, Boolean isCriteria,
                      LocalDateTime createdDate, Integer createdById,
                      LocalDateTime modifiedDate, Integer modifiedById) {
        this.projectId = projectId;
        this.code = code;
        this.name = name;
        this.parentProjectId = parentProjectId;
        this.period = period;
        this.isActive = isActive;
        this.projectGroup = projectGroup;
        this.projectType = projectType;
        this.investmentGrade = investmentGrade;
        this.budgetTypeId = budgetTypeId;
        this.targetProgramId = targetProgramId;
        this.inor = inor;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.constructionLocation = constructionLocation;
        this.designCapacity = designCapacity;
        this.districtType = districtType;
        this.isCriteria = isCriteria;
        this.createdDate = createdDate;
        this.createdById = createdById;
        this.modifiedDate = modifiedDate;
        this.modifiedById = modifiedById;
    }

    // Map Entity to DTO
    public static ProjectDTO fromEntity(Project project) {
        return new ProjectDTO(
                project.getProjectId(),
                project.getCode(),
                project.getName(),
                project.getParentProject() != null ? project.getParentProject().getProjectId() : null,
                project.getPeriod(),
                project.getIsActive(),
                project.getProjectGroup(),
                project.getProjectType(),
                project.getInvestmentGrade(),
                project.getBudgetType() != null ? project.getBudgetType().getBudgetTypeId() : null,
                project.getTargetProgram() != null ? project.getTargetProgram().getTargetProgramId() : null,
                project.getInor(),
                project.getStartDate(),
                project.getFinishDate(),
                project.getConstructionLocation(),
                project.getDesignCapacity(),
                project.getDistrictType(),
                project.getIsCriteria(),
                project.getCreatedDate(),
                project.getCreatedBy() != null ? project.getCreatedBy().getEmployeeId() : null,
                project.getModifiedDate(),
                project.getModifiedBy() != null ? project.getModifiedBy().getEmployeeId() : null
        );
    }

    // Map DTO to Entity
    public Project toEntity() {
        Project project = new Project();
        project.setProjectId(this.projectId);
        project.setCode(this.code);
        project.setName(this.name);
        project.setPeriod(this.period);
        project.setIsActive(this.isActive);
        project.setProjectGroup(this.projectGroup);
        project.setProjectType(this.projectType);
        project.setInvestmentGrade(this.investmentGrade);
        project.setInor(this.inor);
        project.setStartDate(this.startDate);
        project.setFinishDate(this.finishDate);
        project.setConstructionLocation(this.constructionLocation);
        project.setDesignCapacity(this.designCapacity);
        project.setDistrictType(this.districtType);
        project.setIsCriteria(this.isCriteria);
        project.setCreatedDate(this.createdDate);
        project.setModifiedDate(this.modifiedDate);
        // Note: parentProject, budgetType, targetProgram, createdBy, and modifiedBy are not set here as they require fetching entities
        return project;
    }
}