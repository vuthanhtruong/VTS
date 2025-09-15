package com.example.demo.AssetFixedDecreaseReason.dao;

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
public class AssetFixedDecreaseReasonDAOImpl implements AssetFixedDecreaseReasonDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetFixedDecreaseReasonDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixedDecreaseReason(AssetFixedDecreaseReason assetFixedDecreaseReason) {
        List<String> errors = new ArrayList<>();
        if (assetFixedDecreaseReason.getCode() == null || assetFixedDecreaseReason.getCode().trim().isEmpty()) {
            errors.add("Asset fixed decrease reason code is required.");
        } else if (assetFixedDecreaseReason.getCode().length() > 50) {
            errors.add("Asset fixed decrease reason code must not exceed 50 characters.");
        } else if (isAssetFixedDecreaseReasonCodeDuplicate(assetFixedDecreaseReason.getCode(), assetFixedDecreaseReason.getAssetFixedDecreaseReasonId())) {
            errors.add("Asset fixed decrease reason code is already in use.");
        }
        if (assetFixedDecreaseReason.getName() == null || !isValidName(assetFixedDecreaseReason.getName())) {
            errors.add("Asset fixed decrease reason name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixedDecreaseReason.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (assetFixedDecreaseReason.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixedDecreaseReason.getCreateBy() != null && assetFixedDecreaseReason.getCreateBy().getEmployeeId() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixedDecreaseReason.getCreateBy().getEmployeeId());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixedDecreaseReason.getCreateBy().getEmployeeId());
            }
        }
        if (assetFixedDecreaseReason.getModifiedBy() != null && assetFixedDecreaseReason.getModifiedBy().getEmployeeId() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixedDecreaseReason.getModifiedBy().getEmployeeId());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixedDecreaseReason.getModifiedBy().getEmployeeId());
            }
        }
        if (assetFixedDecreaseReason.getDescription() != null && assetFixedDecreaseReason.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        return errors;
    }

    @Override
    public AssetFixedDecreaseReason createAssetFixedDecreaseReason(AssetFixedDecreaseReason assetFixedDecreaseReason) {
        try {
            if (assetFixedDecreaseReason.getCreateBy() != null && assetFixedDecreaseReason.getCreateBy().getEmployeeId() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixedDecreaseReason.getCreateBy().getEmployeeId());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixedDecreaseReason.getCreateBy().getEmployeeId());
                }
                assetFixedDecreaseReason.setCreateBy(createBy);
                assetFixedDecreaseReason.setModifiedBy(createBy);
            }
            entityManager.persist(assetFixedDecreaseReason);
            return assetFixedDecreaseReason;
        } catch (Exception e) {
            logger.error("Failed to create asset fixed decrease reason with code {}: {}", assetFixedDecreaseReason.getCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDecreaseReason getAssetFixedDecreaseReasonById(Integer id) {
        try {
            AssetFixedDecreaseReason assetFixedDecreaseReason = entityManager.find(AssetFixedDecreaseReason.class, id);
            if (assetFixedDecreaseReason == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + id);
            }
            return assetFixedDecreaseReason;
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed decrease reason with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedDecreaseReason> getAllAssetFixedDecreaseReasons() {
        try {
            TypedQuery<AssetFixedDecreaseReason> query = entityManager.createQuery("SELECT afdr FROM AssetFixedDecreaseReason afdr", AssetFixedDecreaseReason.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixed decrease reasons: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDecreaseReason updateAssetFixedDecreaseReason(Integer id, AssetFixedDecreaseReason assetFixedDecreaseReason) {
        try {
            AssetFixedDecreaseReason existingAssetFixedDecreaseReason = entityManager.find(AssetFixedDecreaseReason.class, id);
            if (existingAssetFixedDecreaseReason == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + id);
            }
            existingAssetFixedDecreaseReason.setCode(assetFixedDecreaseReason.getCode());
            existingAssetFixedDecreaseReason.setName(assetFixedDecreaseReason.getName());
            existingAssetFixedDecreaseReason.setIsUse(assetFixedDecreaseReason.getIsUse());
            existingAssetFixedDecreaseReason.setIsActive(assetFixedDecreaseReason.getIsActive());
            existingAssetFixedDecreaseReason.setDescription(assetFixedDecreaseReason.getDescription());
            if (assetFixedDecreaseReason.getModifiedBy() != null && assetFixedDecreaseReason.getModifiedBy().getEmployeeId() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixedDecreaseReason.getModifiedBy().getEmployeeId());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixedDecreaseReason.getModifiedBy().getEmployeeId());
                }
                existingAssetFixedDecreaseReason.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixedDecreaseReason);
            return existingAssetFixedDecreaseReason;
        } catch (Exception e) {
            logger.error("Failed to update asset fixed decrease reason with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAssetFixedDecreaseReason(Integer id) {
        try {
            AssetFixedDecreaseReason assetFixedDecreaseReason = entityManager.find(AssetFixedDecreaseReason.class, id);
            if (assetFixedDecreaseReason == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + id);
            }
            entityManager.remove(assetFixedDecreaseReason);
        } catch (Exception e) {
            logger.error("Failed to delete asset fixed decrease reason with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isAssetFixedDecreaseReasonCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(afdr) FROM AssetFixedDecreaseReason afdr WHERE afdr.code = :code";
        if (excludeId != null) {
            jpql += " AND afdr.assetFixedDecreaseReasonId != :excludeId";
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