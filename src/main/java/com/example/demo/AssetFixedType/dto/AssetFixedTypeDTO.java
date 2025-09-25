package com.example.demo.AssetFixedType.dto;

import com.example.demo.AssetFixedType.model.AssetFixedType;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetFixedTypeDTO {

    private Integer assetFixedTypeId;
    private String code;
    private String name;
    private Integer parentId;
    private Integer lastYearUseTime;
    private Integer newYearUseTime;
    private BigDecimal oldDepreciationRate;
    private BigDecimal newDepreciationRate;
    private Integer yearApplication;
    private Boolean isUse;
    private Boolean isActive;
    private Integer createById;
    private LocalDateTime createDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;
    private String description;

    public AssetFixedTypeDTO() {}

    public AssetFixedTypeDTO(Integer assetFixedTypeId, String code, String name, Integer parentId,
                             Integer lastYearUseTime, Integer newYearUseTime, BigDecimal oldDepreciationRate,
                             BigDecimal newDepreciationRate, Integer yearApplication, Boolean isUse,
                             Boolean isActive, Integer createById, LocalDateTime createDate,
                             Integer modifiedById, LocalDateTime modifiedDate, String description) {
        this.assetFixedTypeId = assetFixedTypeId;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.lastYearUseTime = lastYearUseTime;
        this.newYearUseTime = newYearUseTime;
        this.oldDepreciationRate = oldDepreciationRate;
        this.newDepreciationRate = newDepreciationRate;
        this.yearApplication = yearApplication;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createById = createById;
        this.createDate = createDate;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }

    // Map Entity to DTO
    public static AssetFixedTypeDTO fromEntity(AssetFixedType assetFixedType) {
        return new AssetFixedTypeDTO(
                assetFixedType.getAssetFixedTypeId(),
                assetFixedType.getCode(),
                assetFixedType.getName(),
                assetFixedType.getAssetFixedTypeParent() != null ? assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId() : null,
                assetFixedType.getLastYearUseTime(),
                assetFixedType.getNewYearUseTime(),
                assetFixedType.getOldDepreciationRate(),
                assetFixedType.getNewDepreciationRate(),
                assetFixedType.getYearApplication(),
                assetFixedType.getIsUse(),
                assetFixedType.getIsActive(),
                assetFixedType.getCreateBy() != null ? assetFixedType.getCreateBy().getEmployeeId() : null,
                assetFixedType.getCreateDate(),
                assetFixedType.getModifiedBy() != null ? assetFixedType.getModifiedBy().getEmployeeId() : null,
                assetFixedType.getModifiedDate(),
                assetFixedType.getDescription()
        );
    }

    // Map DTO to Entity
    public AssetFixedType toEntity() {
        AssetFixedType assetFixedType = new AssetFixedType();
        assetFixedType.setAssetFixedTypeId(this.assetFixedTypeId);
        assetFixedType.setCode(this.code);
        assetFixedType.setName(this.name);
        assetFixedType.setLastYearUseTime(this.lastYearUseTime);
        assetFixedType.setNewYearUseTime(this.newYearUseTime);
        assetFixedType.setOldDepreciationRate(this.oldDepreciationRate);
        assetFixedType.setNewDepreciationRate(this.newDepreciationRate);
        assetFixedType.setYearApplication(this.yearApplication);
        assetFixedType.setIsUse(this.isUse);
        assetFixedType.setIsActive(this.isActive);
        assetFixedType.setCreateDate(this.createDate);
        assetFixedType.setModifiedDate(this.modifiedDate);
        assetFixedType.setDescription(this.description);
        // Note: assetFixedTypeParent, createBy, and modifiedBy are not set here as they require fetching entities
        return assetFixedType;
    }
}