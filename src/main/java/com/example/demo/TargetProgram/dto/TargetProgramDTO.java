package com.example.demo.TargetProgram.dto;

import com.example.demo.Employee.model.Employee;
import com.example.demo.TargetProgram.model.TargetProgram;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TargetProgramDTO {

    private Integer targetProgramId;
    private String code;
    private String name;
    private Integer programType;
    private Integer parentId;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer createdById;
    private Integer modifiedById;

    public TargetProgramDTO() {}

    public TargetProgramDTO(Integer targetProgramId, String code, String name, Integer programType,
                            Integer parentId, String description, Boolean isActive,
                            LocalDateTime createdDate, LocalDateTime modifiedDate,
                            Integer createdById, Integer modifiedById) {
        this.targetProgramId = targetProgramId;
        this.code = code;
        this.name = name;
        this.programType = programType;
        this.parentId = parentId;
        this.description = description;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdById = createdById;
        this.modifiedById = modifiedById;
    }

    // Map Entity to DTO
    public static TargetProgramDTO fromEntity(TargetProgram targetProgram) {
        return new TargetProgramDTO(
                targetProgram.getTargetProgramId(),
                targetProgram.getCode(),
                targetProgram.getName(),
                targetProgram.getProgramType(),
                targetProgram.getParent() != null ? targetProgram.getParent().getTargetProgramId() : null,
                targetProgram.getDescription(),
                targetProgram.getIsActive(),
                targetProgram.getCreatedDate(),
                targetProgram.getModifiedDate(),
                targetProgram.getCreatedBy() != null ? targetProgram.getCreatedBy().getEmployeeId() : null,
                targetProgram.getModifiedBy() != null ? targetProgram.getModifiedBy().getEmployeeId() : null
        );
    }

    // Map DTO to Entity
    public TargetProgram toEntity() {
        TargetProgram targetProgram = new TargetProgram();
        targetProgram.setTargetProgramId(this.targetProgramId);
        targetProgram.setCode(this.code);
        targetProgram.setName(this.name);
        targetProgram.setProgramType(this.programType);
        targetProgram.setDescription(this.description);
        targetProgram.setIsActive(this.isActive);
        targetProgram.setCreatedDate(this.createdDate);
        targetProgram.setModifiedDate(this.modifiedDate);
        // Note: parent, createdBy, and modifiedBy are not set here as they require fetching entities
        return targetProgram;
    }
}