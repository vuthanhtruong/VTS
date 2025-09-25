package com.example.demo.Voucher.service;

import com.example.demo.Voucher.dto.VoucherDTO;
import java.util.List;

public interface VoucherService {
    List<String> validateVoucher(VoucherDTO voucherDTO);
    VoucherDTO createVoucher(VoucherDTO voucherDTO);
    VoucherDTO getVoucherById(Integer id);
    List<VoucherDTO> getAllVouchers();
    VoucherDTO updateVoucher(Integer id, VoucherDTO voucherDTO);
    void deleteVoucher(Integer id);
}