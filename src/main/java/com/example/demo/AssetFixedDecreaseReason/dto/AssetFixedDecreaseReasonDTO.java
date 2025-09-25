package com.example.demo.AssetFixedDecreaseReason.dto;

import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetFixedDecreaseReasonDTO {

    private Integer assetFixedDecreaseReasonId;
    private String code;
    private String name;
    private Boolean isUse;
    private Boolean isActive;
    private Integer createById;
    private LocalDateTime createDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;
    private String description;

    public AssetFixedDecreaseReasonDTO() {}

    public AssetFixedDecreaseReasonDTO(Integer assetFixedDecreaseReasonId, String code, String name,
                                       Boolean isUse, Boolean isActive, Integer createById,
                                       LocalDateTime createDate, Integer modifiedById,
                                       LocalDateTime modifiedDate, String description) {
        this.assetFixedDecreaseReasonId = assetFixedDecreaseReasonId;
        this.code = code;
        this.name = name;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createById = createById;
        this.createDate = createDate;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }

    // Map Entity to DTO
    public static AssetFixedDecreaseReasonDTO fromEntity(AssetFixedDecreaseReason assetFixedDecreaseReason) {
        return new AssetFixedDecreaseReasonDTO(
                assetFixedDecreaseReason.getAssetFixedDecreaseReasonId(),
                assetFixedDecreaseReason.getCode(),
                assetFixedDecreaseReason.getName(),
                assetFixedDecreaseReason.getIsUse(),
                assetFixedDecreaseReason.getIsActive(),
                assetFixedDecreaseReason.getCreateBy() != null ? assetFixedDecreaseReason.getCreateBy().getEmployeeId() : null,
                assetFixedDecreaseReason.getCreateDate(),
                assetFixedDecreaseReason.getModifiedBy() != null ? assetFixedDecreaseReason.getModifiedBy().getEmployeeId() : null,
                assetFixedDecreaseReason.getModifiedDate(),
                assetFixedDecreaseReason.getDescription()
        );
    }

    // Map DTO to Entity
    public AssetFixedDecreaseReason toEntity() {
        AssetFixedDecreaseReason assetFixedDecreaseReason = new AssetFixedDecreaseReason();
        assetFixedDecreaseReason.setAssetFixedDecreaseReasonId(this.assetFixedDecreaseReasonId);
        assetFixedDecreaseReason.setCode(this.code);
        assetFixedDecreaseReason.setName(this.name);
        assetFixedDecreaseReason.setIsUse(this.isUse);
        assetFixedDecreaseReason.setIsActive(this.isActive);
        assetFixedDecreaseReason.setCreateDate(this.createDate);
        assetFixedDecreaseReason.setModifiedDate(this.modifiedDate);
        assetFixedDecreaseReason.setDescription(this.description);
        // Note: createBy and modifiedBy are not set here as they require fetching Employee entities
        return assetFixedDecreaseReason;
    }
}