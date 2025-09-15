package com.example.demo.Asset.dao;

import com.example.demo.Asset.model.Asset;
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
public class AssetDAOImpl implements AssetDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAsset(Asset asset) {
        List<String> errors = new ArrayList<>();
        if (asset.getCode() == null || asset.getCode().trim().isEmpty()) {
            errors.add("Asset code is required.");
        } else if (asset.getCode().length() > 50) {
            errors.add("Asset code must not exceed 50 characters.");
        } else if (isAssetCodeDuplicate(asset.getCode(), asset.getAssetId())) {
            errors.add("Asset code is already in use.");
        }
        if (asset.getName() == null || !isValidName(asset.getName())) {
            errors.add("Asset name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (asset.getAssetPurchaseId() == null) {
            errors.add("Asset purchase ID is required.");
        }
        if (asset.getType() == null) {
            errors.add("Asset type is required.");
        }
        if (asset.getStatus() == null) {
            errors.add("Asset status is required.");
        }
        if (asset.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (asset.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (asset.getBarcode() != null && asset.getBarcode().length() > 50) {
            errors.add("Barcode must not exceed 50 characters.");
        }
        if (asset.getDescription() != null && asset.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        if (asset.getCreateBy() != null && asset.getCreateBy().getEmployeeId() != null) {
            Employee createBy = entityManager.find(Employee.class, asset.getCreateBy().getEmployeeId());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + asset.getCreateBy().getEmployeeId());
            }
        }
        if (asset.getModifiedBy() != null && asset.getModifiedBy().getEmployeeId() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, asset.getModifiedBy().getEmployeeId());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + asset.getModifiedBy().getEmployeeId());
            }
        }
        return errors;
    }

    @Override
    public Asset createAsset(Asset asset) {
        try {
            if (asset.getCreateBy() != null && asset.getCreateBy().getEmployeeId() != null) {
                Employee createBy = entityManager.find(Employee.class, asset.getCreateBy().getEmployeeId());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + asset.getCreateBy().getEmployeeId());
                }
                asset.setCreateBy(createBy);
                asset.setModifiedBy(createBy);
            }
            entityManager.persist(asset);
            return asset;
        } catch (Exception e) {
            logger.error("Failed to create asset: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Asset getAssetById(Integer id) {
        try {
            Asset asset = entityManager.find(Asset.class, id);
            if (asset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + id);
            }
            return asset;
        } catch (Exception e) {
            logger.error("Failed to retrieve asset with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Asset> getAllAssets() {
        try {
            TypedQuery<Asset> query = entityManager.createQuery("SELECT a FROM Asset a", Asset.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve all assets: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Asset updateAsset(Integer id, Asset asset) {
        try {
            Asset existingAsset = entityManager.find(Asset.class, id);
            if (existingAsset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + id);
            }
            existingAsset.setCode(asset.getCode());
            existingAsset.setName(asset.getName());
            existingAsset.setAssetPurchaseId(asset.getAssetPurchaseId());
            existingAsset.setBarcode(asset.getBarcode());
            existingAsset.setType(asset.getType());
            existingAsset.setStatus(asset.getStatus());
            existingAsset.setIsUse(asset.getIsUse());
            existingAsset.setIsActive(asset.getIsActive());
            existingAsset.setDescription(asset.getDescription());
            if (asset.getModifiedBy() != null && asset.getModifiedBy().getEmployeeId() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, asset.getModifiedBy().getEmployeeId());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + asset.getModifiedBy().getEmployeeId());
                }
                existingAsset.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAsset);
            return existingAsset;
        } catch (Exception e) {
            logger.error("Failed to update asset with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteAsset(Integer id) {
        try {
            Asset asset = entityManager.find(Asset.class, id);
            if (asset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + id);
            }
            entityManager.remove(asset);
        } catch (Exception e) {
            logger.error("Failed to delete asset with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isAssetCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(a) FROM Asset a WHERE a.code = :code";
        if (excludeId != null) {
            jpql += " AND a.assetId != :excludeId";
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