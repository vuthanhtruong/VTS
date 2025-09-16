package com.example.demo.Voucher.model;

import com.example.demo.Employee.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Vouchers")
@Getter
@Setter
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOUCHER_ID", nullable = false)
    private Integer voucherId;

    @Column(name = "VOUCHER_CODE", nullable = false, length = 50)
    private String voucherCode;

    @Column(name = "VOUCHER_NAME", nullable = false, length = 255)
    private String voucherName;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "IS_AUTO_NUMBERING", nullable = false)
    private Boolean isAutoNumbering;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private Employee modifiedBy;

    public Voucher() {}

    public Voucher(Integer voucherId, String voucherCode, String voucherName, Integer displayOrder,
                   Boolean isAutoNumbering, LocalDateTime createdDate, LocalDateTime modifiedDate,
                   Employee createdBy, Employee modifiedBy) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.displayOrder = displayOrder;
        this.isAutoNumbering = isAutoNumbering;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }
}
