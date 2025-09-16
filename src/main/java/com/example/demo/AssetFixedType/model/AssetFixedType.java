package com.example.demo.AssetFixedType.model;

import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "AssetFixedTypes")
@Getter
@Setter
public class AssetFixedType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_FIXED_TYPE_ID", nullable = false)
    private Integer assetFixedTypeId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AssetFixedType assetFixedTypeParent;

    @Column(name = "LAST_YEAR_USE_TIME")
    private Integer lastYearUseTime;

    @Column(name = "NEW_YEAR_USE_TIME")
    private Integer newYearUseTime;

    @Column(name = "OLD_DEPRECIATION_RATE", nullable = false, precision = 18, scale = 4)
    private BigDecimal oldDepreciationRate;

    @Column(name = "NEW_DEPRECIATION_RATE", nullable = false, precision = 18, scale = 4)
    private BigDecimal newDepreciationRate;

    @Column(name = "YEAR_APPLICATION", nullable = false)
    private Integer yearApplication;

    @Column(name = "IS_USE", nullable = false)
    private Boolean isUse;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee createBy;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    public AssetFixedType() {}

    public AssetFixedType(Integer assetFixedTypeId, String code, String name, AssetFixedType assetFixedTypeParent,
                          Integer lastYearUseTime, Integer newYearUseTime, BigDecimal oldDepreciationRate,
                          BigDecimal newDepreciationRate, Integer yearApplication, Boolean isUse, Boolean isActive,
                          Employee createBy, LocalDateTime createDate, Employee modifiedBy, LocalDateTime modifiedDate,
                          String description) {
        this.assetFixedTypeId = assetFixedTypeId;
        this.code = code;
        this.name = name;
        this.assetFixedTypeParent = assetFixedTypeParent;
        this.lastYearUseTime = lastYearUseTime;
        this.newYearUseTime = newYearUseTime;
        this.oldDepreciationRate = oldDepreciationRate;
        this.newDepreciationRate = newDepreciationRate;
        this.yearApplication = yearApplication;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}
