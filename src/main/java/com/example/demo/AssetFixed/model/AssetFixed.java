package com.example.demo.AssetFixed.model;

import com.example.demo.Asset.model.Asset;
import com.example.demo.AssetFixedType.model.AssetFixedType;
import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "AssetFixed")
@Getter
@Setter
public class AssetFixed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_FIXED_ID", nullable = false)
    private Integer assetFixedId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", nullable = false)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_FIXED_TYPE_ID", nullable = true)
    private AssetFixedType assetFixedType;

    @Column(name = "UNIT", length = 50)
    private String unit;

    @Column(name = "ASSET_FIXED_CARD", length = 50)
    private String assetFixedCard;

    @Column(name = "IS_USE", nullable = false)
    private Boolean isUse;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_BY", nullable = true)
    private Employee createBy;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY", nullable = true)
    private Employee modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    public AssetFixed() {}

    public AssetFixed(Integer assetFixedId, String code, String name, Asset asset, AssetFixedType assetFixedType,
                      String unit, String assetFixedCard, Boolean isUse, Boolean isActive, Employee createBy,
                      LocalDateTime createDate, Employee modifiedBy, LocalDateTime modifiedDate, String description) {
        this.assetFixedId = assetFixedId;
        this.code = code;
        this.name = name;
        this.asset = asset;
        this.assetFixedType = assetFixedType;
        this.unit = unit;
        this.assetFixedCard = assetFixedCard;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}
