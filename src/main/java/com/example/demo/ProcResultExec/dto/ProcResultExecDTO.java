package com.example.demo.ProcResultExec.dto;

import com.example.demo.ProcResultExec.model.ProcResultExec;
import com.example.demo.Project.model.Project;
import com.example.demo.Voucher.model.Voucher;
import com.example.demo.Unit.model.Unit;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcResultExecDTO {

    private Integer procResultExecId;
    private Integer projectId;
    private String decisionNo;
    private LocalDateTime decisionDate;
    private Integer voucherId;
    private Integer unitId;
    private LocalDateTime createdDate;
    private Integer createdById;
    private LocalDateTime modifiedDate;
    private Integer modifiedById;

    public ProcResultExecDTO() {}

    public ProcResultExecDTO(Integer procResultExecId, Integer projectId, String decisionNo, LocalDateTime decisionDate,
                             Integer voucherId, Integer unitId, LocalDateTime createdDate, Integer createdById,
                             LocalDateTime modifiedDate, Integer modifiedById) {
        this.procResultExecId = procResultExecId;
        this.projectId = projectId;
        this.decisionNo = decisionNo;
        this.decisionDate = decisionDate;
        this.voucherId = voucherId;
        this.unitId = unitId;
        this.createdDate = createdDate;
        this.createdById = createdById;
        this.modifiedDate = modifiedDate;
        this.modifiedById = modifiedById;
    }

    // Map Entity to DTO
    public static ProcResultExecDTO fromEntity(ProcResultExec procResultExec) {
        return new ProcResultExecDTO(
                procResultExec.getProcResultExecId(),
                procResultExec.getProject() != null ? procResultExec.getProject().getProjectId() : null,
                procResultExec.getDecisionNo(),
                procResultExec.getDecisionDate(),
                procResultExec.getVoucher() != null ? procResultExec.getVoucher().getVoucherId() : null,
                procResultExec.getUnit() != null ? procResultExec.getUnit().getUnitId() : null,
                procResultExec.getCreatedDate(),
                procResultExec.getCreatedBy() != null ? procResultExec.getCreatedBy().getEmployeeId() : null,
                procResultExec.getModifiedDate(),
                procResultExec.getModifiedBy() != null ? procResultExec.getModifiedBy().getEmployeeId() : null
        );
    }

    // Map DTO to Entity
    public ProcResultExec toEntity() {
        ProcResultExec procResultExec = new ProcResultExec();
        procResultExec.setProcResultExecId(this.procResultExecId);
        procResultExec.setDecisionNo(this.decisionNo);
        procResultExec.setDecisionDate(this.decisionDate);
        procResultExec.setCreatedDate(this.createdDate);
        procResultExec.setModifiedDate(this.modifiedDate);
        // Note: project, voucher, unit, createdBy, and modifiedBy are not set here as they require fetching entities
        return procResultExec;
    }
}