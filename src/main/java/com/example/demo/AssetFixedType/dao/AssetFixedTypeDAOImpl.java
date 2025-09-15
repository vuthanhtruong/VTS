package com.example.demo.AssetFixedType.dao;

import com.example.demo.AssetFixedType.model.AssetFixedType;
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
public class AssetFixedTypeDAOImpl implements AssetFixedTypeDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetFixedTypeDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixedType(AssetFixedType assetFixedType) {
        List<String> errors = new ArrayList<>();
        if (assetFixedType.getCode() == null || assetFixedType.getCode().trim().isEmpty()) {
            errors.add("Asset fixed type code is required.");
        } else if (assetFixedType.getCode().length() > 50) {
            errors.add("Asset fixed type code must not exceed 50 characters.");
        } else if (isAssetFixedTypeCodeDuplicate(assetFixedType.getCode(), assetFixedType.getAssetFixedTypeId())) {
            errors.add("Asset fixed type code is already in use.");
        }
        if (assetFixedType.getName() == null || !isValidName(assetFixedType.getName())) {
            errors.add("Asset fixed type name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixedType.getOldDepreciationRate() == null) {
            errors.add("Old depreciation rate is required.");
        }
        if (assetFixedType.getNewDepreciationRate() == null) {
            errors.add("New depreciation rate is required.");
        }
        if (assetFixedType.getYearApplication() == null) {
            errors.add("Year application is required.");
        }
        if (assetFixedType.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (assetFixedType.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixedType.getAssetFixedTypeParent() != null && assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId() != null) {
            AssetFixedType parent = entityManager.find(AssetFixedType.class, assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId());
            if (parent == null) {
                errors.add("Parent asset fixed type not found with ID: " + assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId());
            }
        }
        if (assetFixedType.getCreateBy() != null && assetFixedType.getCreateBy().getEmployeeId() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixedType.getCreateBy().getEmployeeId());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixedType.getCreateBy().getEmployeeId());
            }
        }
        if (assetFixedType.getModifiedBy() != null && assetFixedType.getModifiedBy().getEmployeeId() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixedType.getModifiedBy().getEmployeeId());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixedType.getModifiedBy().getEmployeeId());
            }
        }
        if (assetFixedType.getDescription() != null && assetFixedType.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        return errors;
    }

    @Override
    public AssetFixedType createAssetFixedType(AssetFixedType assetFixedType) {
        try {
            if (assetFixedType.getAssetFixedTypeParent() != null && assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId() != null) {
                AssetFixedType parent = entityManager.find(AssetFixedType.class, assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId());
                if (parent == null) {
                    throw new EntityNotFoundException("Parent asset fixed type not found with ID: " + assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId());
                }
                assetFixedType.setAssetFixedTypeParent(parent);
            }
            if (assetFixedType.getCreateBy() != null && assetFixedType.getCreateBy().getEmployeeId() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixedType.getCreateBy().getEmployeeId());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixedType.getCreateBy().getEmployeeId());
                }
                assetFixedType.setCreateBy(createBy);
                assetFixedType.setModifiedBy(createBy);
            }
            entityManager.persist(assetFixedType);
            return assetFixedType;
        } catch (Exception e) {
            logger.error("Failed to create asset fixed type with code {}: {}", assetFixedType.getCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedType getAssetFixedTypeById(Integer id) {
        try {
            AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, id);
            if (assetFixedType == null) {
                throw new EntityNotFoundException("Asset fixed type not found with ID: " + id);
            }
            return assetFixedType;
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedType> getAllAssetFixedTypes() {
        try {
            TypedQuery<AssetFixedType> query = entityManager.createQuery("SELECT aft FROM AssetFixedType aft", AssetFixedType.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixed types: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedType updateAssetFixedType(Integer id, AssetFixedType assetFixedType) {
        try {
            AssetFixedType existingAssetFixedType = entityManager.find(AssetFixedType.class, id);
            if (existingAssetFixedType == null) {
                throw new EntityNotFoundException("Asset fixed type not found with ID: " + id);
            }
            existingAssetFixedType.setCode(assetFixedType.getCode());
            existingAssetFixedType.setName(assetFixedType.getName());
            existingAssetFixedType.setLastYearUseTime(assetFixedType.getLastYearUseTime());
            existingAssetFixedType.setNewYearUseTime(assetFixedType.getNewYearUseTime());
            existingAssetFixedType.setOldDepreciationRate(assetFixedType.getOldDepreciationRate());
            existingAssetFixedType.setNewDepreciationRate(assetFixedType.getNewDepreciationRate());
            existingAssetFixedType.setYearApplication(assetFixedType.getYearApplication());
            existingAssetFixedType.setIsUse(assetFixedType.getIsUse());
            existingAssetFixedType.setIsActive(assetFixedType.getIsActive());
            existingAssetFixedType.setDescription(assetFixedType.getDescription());
            if (assetFixedType.getAssetFixedTypeParent() != null && assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId() != null) {
                AssetFixedType parent = entityManager.find(AssetFixedType.class, assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId());
                if (parent == null) {
                    throw new EntityNotFoundException("Parent asset fixed type not found with ID: " + assetFixedType.getAssetFixedTypeParent().getAssetFixedTypeId());
                }
                existingAssetFixedType.setAssetFixedTypeParent(parent);
            } else {
                existingAssetFixedType.setAssetFixedTypeParent(null);
            }
            if (assetFixedType.getModifiedBy() != null && assetFixedType.getModifiedBy().getEmployeeId() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixedType.getModifiedBy().getEmployeeId());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixedType.getModifiedBy().getEmployeeId());
                }
                existingAssetFixedType.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixedType);
            return existingAssetFixedType;
        } catch (Exception e) {
            logger.error("Failed to update asset fixed type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAssetFixedType(Integer id) {
        try {
            AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, id);
            if (assetFixedType == null) {
                throw new EntityNotFoundException("Asset fixed type not found with ID: " + id);
            }
            entityManager.remove(assetFixedType);
        } catch (Exception e) {
            logger.error("Failed to delete asset fixed type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isAssetFixedTypeCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(aft) FROM AssetFixedType aft WHERE aft.code = :code";
        if (excludeId != null) {
            jpql += " AND aft.assetFixedTypeId != :excludeId";
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