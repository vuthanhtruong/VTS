package com.example.demo.Asset.model;

import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Assets")
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_ID", nullable = false)
    private Integer assetId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "ASSET_PURCHASE_ID", nullable = false)
    private Integer assetPurchaseId;

    @Column(name = "BARCODE", length = 50)
    private String barcode;

    @Column(name = "TYPE", nullable = false)
    private Integer type;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

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

    public Asset() {}

    public Asset(Integer assetId, String code, String name, Integer assetPurchaseId, String barcode,
                 Integer type, Integer status, Boolean isUse, Boolean isActive, Employee createBy,
                 LocalDateTime createDate, Employee modifiedBy, LocalDateTime modifiedDate, String description) {
        this.assetId = assetId;
        this.code = code;
        this.name = name;
        this.assetPurchaseId = assetPurchaseId;
        this.barcode = barcode;
        this.type = type;
        this.status = status;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}
