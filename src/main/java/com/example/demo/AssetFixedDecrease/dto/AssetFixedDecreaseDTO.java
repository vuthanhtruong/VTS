package com.example.demo.AssetFixedDecrease.dto;

import com.example.demo.AssetFixed.model.AssetFixed;
import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;
import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetFixedDecreaseDTO {

    private Integer assetFixedDecreaseId;
    private String code;
    private String name;
    private Integer assetFixedTypeId;
    private Integer assetFixedId;
    private String voucherNo;
    private LocalDateTime voucherDate;
    private Integer voucherUserId;
    private Integer type;
    private String invoiceNo;
    private LocalDateTime invoiceDate;
    private Integer assetFixedDecreaseReasonId;
    private Integer decRev;
    private Integer depAccUnpaid;
    private Integer depAccPaid;
    private Integer assetProcCost;
    private Boolean isActive;
    private Integer createById;
    private LocalDateTime createDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;
    private String description;

    public AssetFixedDecreaseDTO() {}

    public AssetFixedDecreaseDTO(Integer assetFixedDecreaseId, String code, String name, Integer assetFixedTypeId,
                                 Integer assetFixedId, String voucherNo, LocalDateTime voucherDate, Integer voucherUserId,
                                 Integer type, String invoiceNo, LocalDateTime invoiceDate, Integer assetFixedDecreaseReasonId,
                                 Integer decRev, Integer depAccUnpaid, Integer depAccPaid, Integer assetProcCost,
                                 Boolean isActive, Integer createById, LocalDateTime createDate,
                                 Integer modifiedById, LocalDateTime modifiedDate, String description) {
        this.assetFixedDecreaseId = assetFixedDecreaseId;
        this.code = code;
        this.name = name;
        this.assetFixedTypeId = assetFixedTypeId;
        this.assetFixedId = assetFixedId;
        this.voucherNo = voucherNo;
        this.voucherDate = voucherDate;
        this.voucherUserId = voucherUserId;
        this.type = type;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.assetFixedDecreaseReasonId = assetFixedDecreaseReasonId;
        this.decRev = decRev;
        this.depAccUnpaid = depAccUnpaid;
        this.depAccPaid = depAccPaid;
        this.assetProcCost = assetProcCost;
        this.isActive = isActive;
        this.createById = createById;
        this.createDate = createDate;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }

    // Map Entity to DTO
    public static AssetFixedDecreaseDTO fromEntity(AssetFixedDecrease assetFixedDecrease) {
        return new AssetFixedDecreaseDTO(
                assetFixedDecrease.getAssetFixedDecreaseId(),
                assetFixedDecrease.getCode(),
                assetFixedDecrease.getName(),
                assetFixedDecrease.getAssetFixedType() != null ? assetFixedDecrease.getAssetFixedType().getAssetFixedId() : null,
                assetFixedDecrease.getAssetFixed() != null ? assetFixedDecrease.getAssetFixed().getAssetFixedId() : null,
                assetFixedDecrease.getVoucherNo(),
                assetFixedDecrease.getVoucherDate(),
                assetFixedDecrease.getVoucherUser() != null ? assetFixedDecrease.getVoucherUser().getEmployeeId() : null,
                assetFixedDecrease.getType(),
                assetFixedDecrease.getInvoiceNo(),
                assetFixedDecrease.getInvoiceDate(),
                assetFixedDecrease.getAssetFixedDecreaseReason() != null ? assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId() : null,
                assetFixedDecrease.getDecRev(),
                assetFixedDecrease.getDepAccUnpaid(),
                assetFixedDecrease.getDepAccPaid(),
                assetFixedDecrease.getAssetProcCost(),
                assetFixedDecrease.getIsActive(),
                assetFixedDecrease.getCreateBy() != null ? assetFixedDecrease.getCreateBy().getEmployeeId() : null,
                assetFixedDecrease.getCreateDate(),
                assetFixedDecrease.getModifiedBy() != null ? assetFixedDecrease.getModifiedBy().getEmployeeId() : null,
                assetFixedDecrease.getModifiedDate(),
                assetFixedDecrease.getDescription()
        );
    }

    // Map DTO to Entity
    public AssetFixedDecrease toEntity() {
        AssetFixedDecrease assetFixedDecrease = new AssetFixedDecrease();
        assetFixedDecrease.setAssetFixedDecreaseId(this.assetFixedDecreaseId);
        assetFixedDecrease.setCode(this.code);
        assetFixedDecrease.setName(this.name);
        assetFixedDecrease.setVoucherNo(this.voucherNo);
        assetFixedDecrease.setVoucherDate(this.voucherDate);
        assetFixedDecrease.setType(this.type);
        assetFixedDecrease.setInvoiceNo(this.invoiceNo);
        assetFixedDecrease.setInvoiceDate(this.invoiceDate);
        assetFixedDecrease.setDecRev(this.decRev);
        assetFixedDecrease.setDepAccUnpaid(this.depAccUnpaid);
        assetFixedDecrease.setDepAccPaid(this.depAccPaid);
        assetFixedDecrease.setAssetProcCost(this.assetProcCost);
        assetFixedDecrease.setIsActive(this.isActive);
        assetFixedDecrease.setCreateDate(this.createDate);
        assetFixedDecrease.setModifiedDate(this.modifiedDate);
        assetFixedDecrease.setDescription(this.description);
        // Note: assetFixedType, assetFixed, voucherUser, assetFixedDecreaseReason, createBy, and modifiedBy are not set here as they require fetching entities
        return assetFixedDecrease;
    }
}