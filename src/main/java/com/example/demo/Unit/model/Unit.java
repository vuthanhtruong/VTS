package com.example.demo.Unit.model;

import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Units")
@Getter
@Setter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID", nullable = false)
    private Integer unitId;

    @Column(name = "TYPE", nullable = false, length = 50)
    private String type;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Column(name = "LEVEL", nullable = false, length = 50)
    private String level;

    @Column(name = "ADDRESS", nullable = false, length = 255)
    private String address;

    @Column(name = "PROVINCE", nullable = false, length = 50)
    private String province;

    @Column(name = "COMMUNE", nullable = false, length = 50)
    private String commune;

    @Column(name = "UNIT_BUDGET_PARENT_ID")
    private Integer unitBudgetParentId;

    @Column(name = "UNIT_PARENT_ID")
    private Integer unitParentId;

    @Column(name = "LOCAL_CODE", length = 50)
    private String localCode;

    @Column(name = "TAX_CODE", length = 50)
    private String taxCode;

    @Column(name = "PHONE_NUMBER", length = 50)
    private String phoneNumber;

    @Column(name = "FAX", length = 50)
    private String fax;

    @Column(name = "BANK_ACCOUNT", length = 50)
    private String bankAccount;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "WEBSITE", length = 255)
    private String website;

    @Column(name = "TREASURY_ACCOUNT", length = 50)
    private String treasuryAccount;

    @Column(name = "TREASURY_CODE", length = 50)
    private String treasuryCode;

    @Column(name = "TREASURY_NAME", length = 255)
    private String treasuryName;

    @Column(name = "TREASURY_ADDRESS", length = 50)
    private String treasuryAddress;

    @Column(name = "WITHHOLDING_RATE")
    private Integer withholdingRate;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATE_BY")
    private Employee createBy;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private Employee modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    public Unit() {}

    public Unit(Integer unitId,String type, String code, String name, String level,
                String address, String province, String commune, Integer unitBudgetParentId, Integer unitParentId,
                String localCode, String taxCode, String phoneNumber, String fax, String bankAccount,
                String email, String website, String treasuryAccount, String treasuryCode, String treasuryName,
                String treasuryAddress, Integer withholdingRate, Boolean isActive,
                Employee createBy, LocalDateTime createDate,
                Employee modifiedBy, LocalDateTime modifiedDate, String description) {
        this.unitId = unitId;
        this.type = type;
        this.code = code;
        this.name = name;
        this.level = level;
        this.address = address;
        this.province = province;
        this.commune = commune;
        this.unitBudgetParentId = unitBudgetParentId;
        this.unitParentId = unitParentId;
        this.localCode = localCode;
        this.taxCode = taxCode;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.bankAccount = bankAccount;
        this.email = email;
        this.website = website;
        this.treasuryAccount = treasuryAccount;
        this.treasuryCode = treasuryCode;
        this.treasuryName = treasuryName;
        this.treasuryAddress = treasuryAddress;
        this.withholdingRate = withholdingRate;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}
