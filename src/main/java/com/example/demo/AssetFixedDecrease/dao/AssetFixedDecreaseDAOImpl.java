package com.example.demo.AssetFixedDecrease.dao;

import com.example.demo.AssetFixed.model.AssetFixed;
import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;
import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import com.example.demo.Employee.model.Employee;
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

@Repository
@Transactional
public class AssetFixedDecreaseDAOImpl implements AssetFixedDecreaseDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetFixedDecreaseDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixedDecrease(AssetFixedDecrease assetFixedDecrease) {
        List<String> errors = new ArrayList<>();
        if (assetFixedDecrease.getCode() == null || assetFixedDecrease.getCode().trim().isEmpty()) {
            errors.add("Asset fixed decrease code is required.");
        } else if (assetFixedDecrease.getCode().length() > 50) {
            errors.add("Asset fixed decrease code must not exceed 50 characters.");
        } else if (isAssetFixedDecreaseCodeDuplicate(assetFixedDecrease.getCode(), assetFixedDecrease.getAssetFixedDecreaseId())) {
            errors.add("Asset fixed decrease code is already in use.");
        }
        if (assetFixedDecrease.getName() == null || !isValidName(assetFixedDecrease.getName())) {
            errors.add("Asset fixed decrease name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixedDecrease.getAssetFixed() != null && assetFixedDecrease.getAssetFixed().getAssetFixedId() != null) {
            AssetFixed assetFixed = entityManager.find(AssetFixed.class, assetFixedDecrease.getAssetFixed().getAssetFixedId());
            if (assetFixed == null) {
                errors.add("Asset fixed not found with ID: " + assetFixedDecrease.getAssetFixed().getAssetFixedId());
            }
        }
        if (assetFixedDecrease.getAssetFixedDecreaseReason() == null || assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId() == null) {
            errors.add("Asset fixed decrease reason is required.");
        } else {
            AssetFixedDecreaseReason reason = entityManager.find(AssetFixedDecreaseReason.class, assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId());
            if (reason == null) {
                errors.add("Asset fixed decrease reason not found with ID: " + assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId());
            }
        }
        if (assetFixedDecrease.getVoucherNo() != null && assetFixedDecrease.getVoucherNo().length() > 50) {
            errors.add("Voucher number must not exceed 50 characters.");
        }
        if (assetFixedDecrease.getInvoiceNo() != null && assetFixedDecrease.getInvoiceNo().length() > 50) {
            errors.add("Invoice number must not exceed 50 characters.");
        }
        if (assetFixedDecrease.getType() == null) {
            errors.add("Type is required.");
        }
        if (assetFixedDecrease.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixedDecrease.getCreateBy() != null && assetFixedDecrease.getCreateBy().getEmployeeId() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixedDecrease.getCreateBy().getEmployeeId());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixedDecrease.getCreateBy().getEmployeeId());
            }
        }
        if (assetFixedDecrease.getModifiedBy() != null && assetFixedDecrease.getModifiedBy().getEmployeeId() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixedDecrease.getModifiedBy().getEmployeeId());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixedDecrease.getModifiedBy().getEmployeeId());
            }
        }
        if (assetFixedDecrease.getDescription() != null && assetFixedDecrease.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        return errors;
    }

    @Override
    public AssetFixedDecrease createAssetFixedDecrease(AssetFixedDecrease assetFixedDecrease) {
        try {
            if (assetFixedDecrease.getAssetFixed() != null && assetFixedDecrease.getAssetFixed().getAssetFixedId() != null) {
                AssetFixed assetFixed = entityManager.find(AssetFixed.class, assetFixedDecrease.getAssetFixed().getAssetFixedId());
                if (assetFixed == null) {
                    throw new EntityNotFoundException("Asset fixed not found with ID: " + assetFixedDecrease.getAssetFixed().getAssetFixedId());
                }
                assetFixedDecrease.setAssetFixed(assetFixed);
            }
            if (assetFixedDecrease.getAssetFixedDecreaseReason() == null || assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId() == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason is required.");
            }
            AssetFixedDecreaseReason reason = entityManager.find(AssetFixedDecreaseReason.class, assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId());
            if (reason == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId());
            }
            assetFixedDecrease.setAssetFixedDecreaseReason(reason);
            if (assetFixedDecrease.getCreateBy() != null && assetFixedDecrease.getCreateBy().getEmployeeId() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixedDecrease.getCreateBy().getEmployeeId());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixedDecrease.getCreateBy().getEmployeeId());
                }
                assetFixedDecrease.setCreateBy(createBy);
                assetFixedDecrease.setModifiedBy(createBy);
            }
            entityManager.persist(assetFixedDecrease);
            return assetFixedDecrease;
        } catch (Exception e) {
            logger.error("Failed to create asset fixed decrease with code {}: {}", assetFixedDecrease.getCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDecrease getAssetFixedDecreaseById(Integer id) {
        try {
            AssetFixedDecrease assetFixedDecrease = entityManager.find(AssetFixedDecrease.class, id);
            if (assetFixedDecrease == null) {
                throw new EntityNotFoundException("Asset fixed decrease not found with ID: " + id);
            }
            return assetFixedDecrease;
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed decrease with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedDecrease> getAllAssetFixedDecreases() {
        try {
            TypedQuery<AssetFixedDecrease> query = entityManager.createQuery("SELECT afd FROM AssetFixedDecrease afd", AssetFixedDecrease.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixed decreases: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDecrease updateAssetFixedDecrease(Integer id, AssetFixedDecrease assetFixedDecrease) {
        try {
            AssetFixedDecrease existingAssetFixedDecrease = entityManager.find(AssetFixedDecrease.class, id);
            if (existingAssetFixedDecrease == null) {
                throw new EntityNotFoundException("Asset fixed decrease not found with ID: " + id);
            }
            existingAssetFixedDecrease.setCode(assetFixedDecrease.getCode());
            existingAssetFixedDecrease.setName(assetFixedDecrease.getName());
            if (assetFixedDecrease.getAssetFixed() != null && assetFixedDecrease.getAssetFixed().getAssetFixedId() != null) {
                AssetFixed assetFixed = entityManager.find(AssetFixed.class, assetFixedDecrease.getAssetFixed().getAssetFixedId());
                if (assetFixed == null) {
                    throw new EntityNotFoundException("Asset fixed not found with ID: " + assetFixedDecrease.getAssetFixed().getAssetFixedId());
                }
                existingAssetFixedDecrease.setAssetFixed(assetFixed);
            } else {
                existingAssetFixedDecrease.setAssetFixed(null);
            }
            if (assetFixedDecrease.getAssetFixedDecreaseReason() != null && assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId() != null) {
                AssetFixedDecreaseReason reason = entityManager.find(AssetFixedDecreaseReason.class, assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId());
                if (reason == null) {
                    throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + assetFixedDecrease.getAssetFixedDecreaseReason().getAssetFixedDecreaseReasonId());
                }
                existingAssetFixedDecrease.setAssetFixedDecreaseReason(reason);
            }
            existingAssetFixedDecrease.setVoucherNo(assetFixedDecrease.getVoucherNo());
            existingAssetFixedDecrease.setVoucherDate(assetFixedDecrease.getVoucherDate());
            if (assetFixedDecrease.getVoucherUser() != null && assetFixedDecrease.getVoucherUser().getEmployeeId() != null) {
                Employee voucherUser = entityManager.find(Employee.class, assetFixedDecrease.getVoucherUser().getEmployeeId());
                if (voucherUser == null) {
                    throw new EntityNotFoundException("Voucher user not found with ID: " + assetFixedDecrease.getVoucherUser().getEmployeeId());
                }
                existingAssetFixedDecrease.setVoucherUser(voucherUser);
            } else {
                existingAssetFixedDecrease.setVoucherUser(null);
            }
            existingAssetFixedDecrease.setType(assetFixedDecrease.getType());
            existingAssetFixedDecrease.setInvoiceNo(assetFixedDecrease.getInvoiceNo());
            existingAssetFixedDecrease.setInvoiceDate(assetFixedDecrease.getInvoiceDate());
            existingAssetFixedDecrease.setDecRev(assetFixedDecrease.getDecRev());
            existingAssetFixedDecrease.setDepAccUnpaid(assetFixedDecrease.getDepAccUnpaid());
            existingAssetFixedDecrease.setDepAccPaid(assetFixedDecrease.getDepAccPaid());
            existingAssetFixedDecrease.setAssetProcCost(assetFixedDecrease.getAssetProcCost());
            existingAssetFixedDecrease.setIsActive(assetFixedDecrease.getIsActive());
            existingAssetFixedDecrease.setDescription(assetFixedDecrease.getDescription());
            if (assetFixedDecrease.getModifiedBy() != null && assetFixedDecrease.getModifiedBy().getEmployeeId() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixedDecrease.getModifiedBy().getEmployeeId());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixedDecrease.getModifiedBy().getEmployeeId());
                }
                existingAssetFixedDecrease.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixedDecrease);
            return existingAssetFixedDecrease;
        } catch (Exception e) {
            logger.error("Failed to update asset fixed decrease with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAssetFixedDecrease(Integer id) {
        try {
            AssetFixedDecrease assetFixedDecrease = entityManager.find(AssetFixedDecrease.class, id);
            if (assetFixedDecrease == null) {
                throw new EntityNotFoundException("Asset fixed decrease not found with ID: " + id);
            }
            entityManager.remove(assetFixedDecrease);
        } catch (Exception e) {
            logger.error("Failed to delete asset fixed decrease with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isAssetFixedDecreaseCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(afd) FROM AssetFixedDecrease afd WHERE afd.code = :code";
        if (excludeId != null) {
            jpql += " AND afd.assetFixedDecreaseId != :excludeId";
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