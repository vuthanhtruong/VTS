package com.example.demo.AssetFixedDecreaseReason.model;

import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "AssetFixedDecreaseReasons")
@Getter
@Setter
public class AssetFixedDecreaseReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_FIXED_DECREASE_REASON_ID", nullable = false)
    private Integer assetFixedDecreaseReasonId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

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

    public AssetFixedDecreaseReason() {}

    public AssetFixedDecreaseReason(Integer assetFixedDecreaseReasonId, String code, String name, Boolean isUse,
                                    Boolean isActive, Employee createBy, LocalDateTime createDate,
                                    Employee modifiedBy, LocalDateTime modifiedDate, String description) {
        this.assetFixedDecreaseReasonId = assetFixedDecreaseReasonId;
        this.code = code;
        this.name = name;
        this.isUse = isUse;
        this.isActive = isActive;
        this.createBy = createBy;
        this.createDate = createDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
        this.description = description;
    }
}
