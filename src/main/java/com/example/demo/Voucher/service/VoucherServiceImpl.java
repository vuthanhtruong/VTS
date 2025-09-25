package com.example.demo.Voucher.service;

import com.example.demo.Voucher.dao.VoucherDAO;
import com.example.demo.Voucher.dto.VoucherDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherDAO voucherDAO;

    public VoucherServiceImpl(VoucherDAO voucherDAO) {
        this.voucherDAO = voucherDAO;
    }

    @Override
    public List<String> validateVoucher(VoucherDTO voucherDTO) {
        return voucherDAO.validateVoucher(voucherDTO);
    }

    @Override
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        return voucherDAO.createVoucher(voucherDTO);
    }

    @Override
    public VoucherDTO getVoucherById(Integer id) {
        return voucherDAO.getVoucherById(id);
    }

    @Override
    public List<VoucherDTO> getAllVouchers() {
        return voucherDAO.getAllVouchers();
    }

    @Override
    public VoucherDTO updateVoucher(Integer id, VoucherDTO voucherDTO) {
        return voucherDAO.updateVoucher(id, voucherDTO);
    }

    @Override
    public void deleteVoucher(Integer id) {
        voucherDAO.deleteVoucher(id);
    }
}