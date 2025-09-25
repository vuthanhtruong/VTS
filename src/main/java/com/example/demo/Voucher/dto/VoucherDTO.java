package com.example.demo.Voucher.dto;

import com.example.demo.Employee.model.Employee;
import com.example.demo.Voucher.model.Voucher;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherDTO {

    private Integer voucherId;
    private String voucherCode;
    private String voucherName;
    private Integer displayOrder;
    private Boolean isAutoNumbering;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer createdById;
    private Integer modifiedById;

    public VoucherDTO() {}

    public VoucherDTO(Integer voucherId, String voucherCode, String voucherName, Integer displayOrder,
                      Boolean isAutoNumbering, LocalDateTime createdDate, LocalDateTime modifiedDate,
                      Integer createdById, Integer modifiedById) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.voucherName = voucherName;
        this.displayOrder = displayOrder;
        this.isAutoNumbering = isAutoNumbering;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdById = createdById;
        this.modifiedById = modifiedById;
    }

    // Map Entity to DTO
    public static VoucherDTO fromEntity(Voucher voucher) {
        return new VoucherDTO(
                voucher.getVoucherId(),
                voucher.getVoucherCode(),
                voucher.getVoucherName(),
                voucher.getDisplayOrder(),
                voucher.getIsAutoNumbering(),
                voucher.getCreatedDate(),
                voucher.getModifiedDate(),
                voucher.getCreatedBy() != null ? voucher.getCreatedBy().getEmployeeId() : null,
                voucher.getModifiedBy() != null ? voucher.getModifiedBy().getEmployeeId() : null
        );
    }

    // Map DTO to Entity
    public Voucher toEntity() {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(this.voucherId);
        voucher.setVoucherCode(this.voucherCode);
        voucher.setVoucherName(this.voucherName);
        voucher.setDisplayOrder(this.displayOrder);
        voucher.setIsAutoNumbering(this.isAutoNumbering);
        voucher.setCreatedDate(this.createdDate);
        voucher.setModifiedDate(this.modifiedDate);
        // Note: createdBy and modifiedBy are not set here as they require fetching Employee entities
        return voucher;
    }
}