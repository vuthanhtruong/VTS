package com.example.demo.AssetFixed.dao;

import com.example.demo.Asset.model.Asset;
import com.example.demo.AssetFixed.model.AssetFixed;
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
public class AssetFixedDAOImpl implements AssetFixedDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetFixedDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixed(AssetFixed assetFixed) {
        List<String> errors = new ArrayList<>();
        if (assetFixed.getCode() == null || assetFixed.getCode().trim().isEmpty()) {
            errors.add("Asset fixed code is required.");
        } else if (assetFixed.getCode().length() > 50) {
            errors.add("Asset fixed code must not exceed 50 characters.");
        } else if (isAssetFixedCodeDuplicate(assetFixed.getCode(), assetFixed.getAssetFixedId())) {
            errors.add("Asset fixed code is already in use.");
        }
        if (assetFixed.getName() == null || !isValidName(assetFixed.getName())) {
            errors.add("Asset fixed name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixed.getAsset() == null || assetFixed.getAsset().getAssetId() == null) {
            errors.add("Asset ID is required.");
        } else {
            Asset asset = entityManager.find(Asset.class, assetFixed.getAsset().getAssetId());
            if (asset == null) {
                errors.add("Asset not found with ID: " + assetFixed.getAsset().getAssetId());
            }
        }
        if (assetFixed.getAssetFixedType() != null && assetFixed.getAssetFixedType().getAssetFixedTypeId() != null) {
            AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, assetFixed.getAssetFixedType().getAssetFixedTypeId());
            if (assetFixedType == null) {
                errors.add("Asset fixed type not found with ID: " + assetFixed.getAssetFixedType().getAssetFixedTypeId());
            }
        }
        if (assetFixed.getUnit() != null && assetFixed.getUnit().length() > 50) {
            errors.add("Unit must not exceed 50 characters.");
        }
        if (assetFixed.getAssetFixedCard() != null && assetFixed.getAssetFixedCard().length() > 50) {
            errors.add("Asset fixed card must not exceed 50 characters.");
        }
        if (assetFixed.getDescription() != null && assetFixed.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        if (assetFixed.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (assetFixed.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixed.getCreateBy() != null && assetFixed.getCreateBy().getEmployeeId() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixed.getCreateBy().getEmployeeId());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixed.getCreateBy().getEmployeeId());
            }
        }
        if (assetFixed.getModifiedBy() != null && assetFixed.getModifiedBy().getEmployeeId() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixed.getModifiedBy().getEmployeeId());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixed.getModifiedBy().getEmployeeId());
            }
        }
        return errors;
    }

    @Override
    public AssetFixed createAssetFixed(AssetFixed assetFixed) {
        try {
            if (assetFixed.getAsset() == null || assetFixed.getAsset().getAssetId() == null) {
                throw new EntityNotFoundException("Asset ID is required.");
            }
            Asset asset = entityManager.find(Asset.class, assetFixed.getAsset().getAssetId());
            if (asset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + assetFixed.getAsset().getAssetId());
            }
            assetFixed.setAsset(asset);
            if (assetFixed.getAssetFixedType() != null && assetFixed.getAssetFixedType().getAssetFixedTypeId() != null) {
                AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, assetFixed.getAssetFixedType().getAssetFixedTypeId());
                if (assetFixedType == null) {
                    throw new EntityNotFoundException("Asset fixed type not found with ID: " + assetFixed.getAssetFixedType().getAssetFixedTypeId());
                }
                assetFixed.setAssetFixedType(assetFixedType);
            }
            if (assetFixed.getCreateBy() != null && assetFixed.getCreateBy().getEmployeeId() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixed.getCreateBy().getEmployeeId());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixed.getCreateBy().getEmployeeId());
                }
                assetFixed.setCreateBy(createBy);
                assetFixed.setModifiedBy(createBy);
            }
            entityManager.persist(assetFixed);
            return assetFixed;
        } catch (Exception e) {
            logger.error("Failed to create asset fixed: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixed getAssetFixedById(Integer id) {
        try {
            AssetFixed assetFixed = entityManager.find(AssetFixed.class, id);
            if (assetFixed == null) {
                throw new EntityNotFoundException("Asset fixed not found with ID: " + id);
            }
            return assetFixed;
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixed> getAllAssetFixed() {
        try {
            TypedQuery<AssetFixed> query = entityManager.createQuery("SELECT af FROM AssetFixed af", AssetFixed.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixeds: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixed updateAssetFixed(Integer id, AssetFixed assetFixed) {
        try {
            AssetFixed existingAssetFixed = entityManager.find(AssetFixed.class, id);
            if (existingAssetFixed == null) {
                throw new EntityNotFoundException("Asset fixed not found with ID: " + id);
            }
            existingAssetFixed.setCode(assetFixed.getCode());
            existingAssetFixed.setName(assetFixed.getName());
            if (assetFixed.getAsset() != null && assetFixed.getAsset().getAssetId() != null) {
                Asset asset = entityManager.find(Asset.class, assetFixed.getAsset().getAssetId());
                if (asset == null) {
                    throw new EntityNotFoundException("Asset not found with ID: " + assetFixed.getAsset().getAssetId());
                }
                existingAssetFixed.setAsset(asset);
            }
            if (assetFixed.getAssetFixedType() != null && assetFixed.getAssetFixedType().getAssetFixedTypeId() != null) {
                AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, assetFixed.getAssetFixedType().getAssetFixedTypeId());
                if (assetFixedType == null) {
                    throw new EntityNotFoundException("Asset fixed type not found with ID: " + assetFixed.getAssetFixedType().getAssetFixedTypeId());
                }
                existingAssetFixed.setAssetFixedType(assetFixedType);
            } else {
                existingAssetFixed.setAssetFixedType(null);
            }
            existingAssetFixed.setUnit(assetFixed.getUnit());
            existingAssetFixed.setAssetFixedCard(assetFixed.getAssetFixedCard());
            existingAssetFixed.setIsUse(assetFixed.getIsUse());
            existingAssetFixed.setIsActive(assetFixed.getIsActive());
            existingAssetFixed.setDescription(assetFixed.getDescription());
            if (assetFixed.getModifiedBy() != null && assetFixed.getModifiedBy().getEmployeeId() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixed.getModifiedBy().getEmployeeId());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixed.getModifiedBy().getEmployeeId());
                }
                existingAssetFixed.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixed);
            return existingAssetFixed;
        } catch (Exception e) {
            logger.error("Failed to update asset fixed with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAssetFixed(Integer id) {
        try {
            AssetFixed assetFixed = entityManager.find(AssetFixed.class, id);
            if (assetFixed == null) {
                throw new EntityNotFoundException("Asset fixed not found with ID: " + id);
            }
            entityManager.remove(assetFixed);
        } catch (Exception e) {
            logger.error("Failed to delete asset fixed with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isAssetFixedCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(af) FROM AssetFixed af WHERE af.code = :code";
        if (excludeId != null) {
            jpql += " AND af.assetFixedId != :excludeId";
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