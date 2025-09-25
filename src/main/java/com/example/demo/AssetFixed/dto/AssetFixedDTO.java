package com.example.demo.AssetFixed.dto;

import com.example.demo.Asset.model.Asset;
import com.example.demo.AssetFixed.model.AssetFixed;
import com.example.demo.AssetFixedType.model.AssetFixedType;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetFixedDTO {

    private Integer assetFixedId;
    private String code;
    private String name;
    private Integer assetId;
    private Integer assetFixedTypeId;
    private String unit;
    private String assetFixedCard;
    private Boolean isUse;
    private Boolean isActive;
    private Integer createById;
    private LocalDateTime createDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;
    private String description;

    public AssetFixedDTO() {}

    public AssetFixedDTO(Integer assetFixedId, String code, String name, Integer assetId, Integer assetFixedTypeId,
                         String unit, String assetFixedCard, Boolean isUse, Boolean isActive, Integer createById,
                         LocalDateTime createDate, Integer modifiedById, LocalDateTime modifiedDate, String description) {
        this.assetFixedId = assetFixedId;
        this.code = code;
        this.name = name;
        this.assetId = assetId;
        this.assetFixedTypeId = assetFixedTypeId;
        this.unit = unit;
        this.assetFixedCard = assetFixedCard;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createById = createById;
        this.createDate = createDate;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }

    // Map Entity to DTO
    public static AssetFixedDTO fromEntity(AssetFixed assetFixed) {
        return new AssetFixedDTO(
                assetFixed.getAssetFixedId(),
                assetFixed.getCode(),
                assetFixed.getName(),
                assetFixed.getAsset() != null ? assetFixed.getAsset().getAssetId() : null,
                assetFixed.getAssetFixedType() != null ? assetFixed.getAssetFixedType().getAssetFixedTypeId() : null,
                assetFixed.getUnit(),
                assetFixed.getAssetFixedCard(),
                assetFixed.getIsUse(),
                assetFixed.getIsActive(),
                assetFixed.getCreateBy() != null ? assetFixed.getCreateBy().getEmployeeId() : null,
                assetFixed.getCreateDate(),
                assetFixed.getModifiedBy() != null ? assetFixed.getModifiedBy().getEmployeeId() : null,
                assetFixed.getModifiedDate(),
                assetFixed.getDescription()
        );
    }

    // Map DTO to Entity
    public AssetFixed toEntity() {
        AssetFixed assetFixed = new AssetFixed();
        assetFixed.setAssetFixedId(this.assetFixedId);
        assetFixed.setCode(this.code);
        assetFixed.setName(this.name);
        assetFixed.setUnit(this.unit);
        assetFixed.setAssetFixedCard(this.assetFixedCard);
        assetFixed.setIsUse(this.isUse);
        assetFixed.setIsActive(this.isActive);
        assetFixed.setCreateDate(this.createDate);
        assetFixed.setModifiedDate(this.modifiedDate);
        assetFixed.setDescription(this.description);
        // Note: asset, assetFixedType, createBy, and modifiedBy are not set here as they require fetching entities
        return assetFixed;
    }
}