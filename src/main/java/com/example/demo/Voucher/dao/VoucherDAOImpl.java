package com.example.demo.Voucher.dao;

import com.example.demo.Employee.model.Employee;
import com.example.demo.Voucher.dto.VoucherDTO;
import com.example.demo.Voucher.model.Voucher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class VoucherDAOImpl implements VoucherDAO {

    private static final Logger logger = LoggerFactory.getLogger(VoucherDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateVoucher(VoucherDTO voucherDTO) {
        List<String> errors = new ArrayList<>();
        if (voucherDTO.getVoucherCode() == null || voucherDTO.getVoucherCode().trim().isEmpty()) {
            errors.add("Voucher code is required.");
        } else if (voucherDTO.getVoucherCode().length() > 50) {
            errors.add("Voucher code must not exceed 50 characters.");
        } else if (isVoucherCodeDuplicate(voucherDTO.getVoucherCode(), voucherDTO.getVoucherId())) {
            errors.add("Voucher code is already in use.");
        }
        if (voucherDTO.getVoucherName() == null || !isValidName(voucherDTO.getVoucherName())) {
            errors.add("Voucher name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (voucherDTO.getIsAutoNumbering() == null) {
            errors.add("Is auto numbering status is required.");
        }
        if (voucherDTO.getCreatedById() != null) {
            Employee createdBy = entityManager.find(Employee.class, voucherDTO.getCreatedById());
            if (createdBy == null) {
                errors.add("Creator Employee not found with ID: " + voucherDTO.getCreatedById());
            }
        }
        if (voucherDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, voucherDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + voucherDTO.getModifiedById());
            }
        }
        return errors;
    }

    @Override
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        try {
            Voucher voucher = voucherDTO.toEntity();
            if (voucherDTO.getCreatedById() != null) {
                Employee createdBy = entityManager.find(Employee.class, voucherDTO.getCreatedById());
                if (createdBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + voucherDTO.getCreatedById());
                }
                voucher.setCreatedBy(createdBy);
                voucher.setModifiedBy(createdBy);
            }
            entityManager.persist(voucher);
            return VoucherDTO.fromEntity(voucher);
        } catch (Exception e) {
            logger.error("Failed to create voucher with code {}: {}", voucherDTO.getVoucherCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public VoucherDTO getVoucherById(Integer id) {
        try {
            Voucher voucher = entityManager.find(Voucher.class, id);
            if (voucher == null) {
                throw new EntityNotFoundException("Voucher not found with ID: " + id);
            }
            return VoucherDTO.fromEntity(voucher);
        } catch (Exception e) {
            logger.error("Failed to retrieve voucher with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<VoucherDTO> getAllVouchers() {
        try {
            TypedQuery<Voucher> query = entityManager.createQuery("SELECT v FROM Voucher v", Voucher.class);
            return query.getResultList().stream()
                    .map(VoucherDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all vouchers: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public VoucherDTO updateVoucher(Integer id, VoucherDTO voucherDTO) {
        try {
            Voucher existingVoucher = entityManager.find(Voucher.class, id);
            if (existingVoucher == null) {
                throw new EntityNotFoundException("Voucher not found with ID: " + id);
            }
            existingVoucher.setVoucherCode(voucherDTO.getVoucherCode());
            existingVoucher.setVoucherName(voucherDTO.getVoucherName());
            existingVoucher.setDisplayOrder(voucherDTO.getDisplayOrder());
            existingVoucher.setIsAutoNumbering(voucherDTO.getIsAutoNumbering());
            existingVoucher.setCreatedDate(voucherDTO.getCreatedDate());
            existingVoucher.setModifiedDate(voucherDTO.getModifiedDate());
            if (voucherDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, voucherDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + voucherDTO.getModifiedById());
                }
                existingVoucher.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingVoucher);
            return VoucherDTO.fromEntity(existingVoucher);
        } catch (Exception e) {
            logger.error("Failed to update voucher with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteVoucher(Integer id) {
        try {
            Voucher voucher = entityManager.find(Voucher.class, id);
            if (voucher == null) {
                throw new EntityNotFoundException("Voucher not found with ID: " + id);
            }
            entityManager.remove(voucher);
        } catch (Exception e) {
            logger.error("Failed to delete voucher with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isVoucherCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(v) FROM Voucher v WHERE v.voucherCode = :code";
        if (excludeId != null) {
            jpql += " AND v.voucherId != :excludeId";
        }
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("code", code);
        if (excludeId != null) {
            query.setParameter("excludeId", excludeId);
        }
        return query.getSingleResult() > 0;
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String nameRegex = "^(?=.{2,255}$)(\\p{L}+[\\p{L}'’\\-\\.]*)((\\s+\\p{L}+[\\p{L}'’\\-\\.]*)*)$";
        return name.matches(nameRegex);
    }
}