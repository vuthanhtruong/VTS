package com.example.demo.AssetFixedDecrease.model;

import com.example.demo.AssetFixed.model.AssetFixed;
import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "AssetFixedDecreases")
@Getter
@Setter
public class AssetFixedDecrease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_FIXED_DECREASE_ID", nullable = false)
    private Integer assetFixedDecreaseId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_FIXED_TYPE_ID", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AssetFixed assetFixedType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_FIXED_ID", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AssetFixed assetFixed;

    @Column(name = "VOUCHER_NO", length = 50)
    private String voucherNo;

    @Column(name = "VOUCHER_DATE")
    private LocalDateTime voucherDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VOUCHER_USER", nullable = true)
    private Employee voucherUser;

    @Column(name = "TYPE", nullable = false)
    private Integer type;

    @Column(name = "INVOICE_NO", length = 50)
    private String invoiceNo;

    @Column(name = "INVOICE_DATE")
    private LocalDateTime invoiceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_FIXED_DECREASE_REASON_ID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AssetFixedDecreaseReason assetFixedDecreaseReason;

    @Column(name = "DEC_REV")
    private Double decRev;

    @Column(name = "DEP_ACC_UNPAID")
    private Double depAccUnpaid;

    @Column(name = "DEP_ACC_PAID")
    private Double depAccPaid;

    @Column(name = "ASSET_PROC_COST")
    private Double assetProcCost;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_BY", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee createBy;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    public AssetFixedDecrease() {}

    public AssetFixedDecrease(Integer assetFixedDecreaseId, String code, String name, AssetFixed assetFixedType,
                              AssetFixed assetFixed, String voucherNo, LocalDateTime voucherDate, Employee voucherUser,
                              Integer type, String invoiceNo, LocalDateTime invoiceDate,
                              AssetFixedDecreaseReason assetFixedDecreaseReason, Double decRev, Double depAccUnpaid,
                              Double depAccPaid, Double assetProcCost, Boolean isActive, Employee createBy,
                              LocalDateTime createDate, Employee modifiedBy, LocalDateTime modifiedDate, String description) {
        this.assetFixedDecreaseId = assetFixedDecreaseId;
        this.code = code;
        this.name = name;
        this.assetFixedType = assetFixedType;
        this.assetFixed = assetFixed;
        this.voucherNo = voucherNo;
        this.voucherDate = voucherDate;
        this.voucherUser = voucherUser;
        this.type = type;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.assetFixedDecreaseReason = assetFixedDecreaseReason;
        this.decRev = decRev;
        this.depAccUnpaid = depAccUnpaid;
        this.depAccPaid = depAccPaid;
        this.assetProcCost = assetProcCost;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}