package com.example.demo.BudgetType.dto;

import com.example.demo.BudgetType.model.BudgetType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetTypeDTO {

    private Integer budgetTypeId;
    private String budgetTypeCode;
    private String budgetTypeName;
    private Integer budgetTypeParentId;
    private Boolean isUse;
    private Boolean isActive;
    private Integer createById;
    private LocalDateTime createDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;
    private String description;

    public BudgetTypeDTO() {}

    public BudgetTypeDTO(Integer budgetTypeId, String budgetTypeCode, String budgetTypeName, Integer budgetTypeParentId,
                         Boolean isUse, Boolean isActive, Integer createById, LocalDateTime createDate,
                         Integer modifiedById, LocalDateTime modifiedDate, String description) {
        this.budgetTypeId = budgetTypeId;
        this.budgetTypeCode = budgetTypeCode;
        this.budgetTypeName = budgetTypeName;
        this.budgetTypeParentId = budgetTypeParentId;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createById = createById;
        this.createDate = createDate;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }

    // Map Entity to DTO
    public static BudgetTypeDTO fromEntity(BudgetType budgetType) {
        return new BudgetTypeDTO(
                budgetType.getBudgetTypeId(),
                budgetType.getBudgetTypeCode(),
                budgetType.getBudgetTypeName(),
                budgetType.getBudgetTypeParent() != null ? budgetType.getBudgetTypeParent().getBudgetTypeId() : null,
                budgetType.getIsUse(),
                budgetType.getIsActive(),
                budgetType.getCreateBy() != null ? budgetType.getCreateBy().getEmployeeId() : null,
                budgetType.getCreateDate(),
                budgetType.getModifiedBy() != null ? budgetType.getModifiedBy().getEmployeeId() : null,
                budgetType.getModifiedDate(),
                budgetType.getDescription()
        );
    }

    // Map DTO to Entity
    public BudgetType toEntity() {
        BudgetType budgetType = new BudgetType();
        budgetType.setBudgetTypeId(this.budgetTypeId);
        budgetType.setBudgetTypeCode(this.budgetTypeCode);
        budgetType.setBudgetTypeName(this.budgetTypeName);
        budgetType.setIsUse(this.isUse);
        budgetType.setIsActive(this.isActive);
        budgetType.setCreateDate(this.createDate);
        budgetType.setModifiedDate(this.modifiedDate);
        budgetType.setDescription(this.description);
        // Note: budgetTypeParent, createBy, and modifiedBy are not set here as they require fetching entities
        return budgetType;
    }
}