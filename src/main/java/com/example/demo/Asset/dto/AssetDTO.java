package com.example.demo.Asset.dto;

import com.example.demo.Asset.model.Asset;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetDTO {

    private Integer assetId;
    private String code;
    private String name;
    private Integer assetPurchaseId;
    private String barcode;
    private Integer type;
    private Integer status;
    private Boolean isUse;
    private Boolean isActive;
    private Integer createById;
    private LocalDateTime createDate;
    private Integer modifiedById;
    private LocalDateTime modifiedDate;
    private String description;

    public AssetDTO() {}

    public AssetDTO(Integer assetId, String code, String name, Integer assetPurchaseId, String barcode,
                    Integer type, Integer status, Boolean isUse, Boolean isActive, Integer createById,
                    LocalDateTime createDate, Integer modifiedById, LocalDateTime modifiedDate, String description) {
        this.assetId = assetId;
        this.code = code;
        this.name = name;
        this.assetPurchaseId = assetPurchaseId;
        this.barcode = barcode;
        this.type = type;
        this.status = status;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createById = createById;
        this.createDate = createDate;
        this.modifiedById = modifiedById;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }

    // Map Entity to DTO
    public static AssetDTO fromEntity(Asset asset) {
        return new AssetDTO(
                asset.getAssetId(),
                asset.getCode(),
                asset.getName(),
                asset.getAssetPurchaseId(),
                asset.getBarcode(),
                asset.getType(),
                asset.getStatus(),
                asset.getIsUse(),
                asset.getIsActive(),
                asset.getCreateBy() != null ? asset.getCreateBy().getEmployeeId() : null,
                asset.getCreateDate(),
                asset.getModifiedBy() != null ? asset.getModifiedBy().getEmployeeId() : null,
                asset.getModifiedDate(),
                asset.getDescription()
        );
    }

    // Map DTO to Entity
    public Asset toEntity() {
        Asset asset = new Asset();
        asset.setAssetId(this.assetId);
        asset.setCode(this.code);
        asset.setName(this.name);
        asset.setAssetPurchaseId(this.assetPurchaseId);
        asset.setBarcode(this.barcode);
        asset.setType(this.type);
        asset.setStatus(this.status);
        asset.setIsUse(this.isUse);
        asset.setIsActive(this.isActive);
        asset.setCreateDate(this.createDate);
        asset.setModifiedDate(this.modifiedDate);
        asset.setDescription(this.description);
        // Note: createBy and modifiedBy are not set here as they require fetching Employee entities
        return asset;
    }
}