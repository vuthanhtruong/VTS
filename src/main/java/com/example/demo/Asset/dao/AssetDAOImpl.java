package com.example.demo.Asset.dao;

import com.example.demo.Asset.dto.AssetDTO;
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
import java.util.stream.Collectors;

@Repository
@Transactional
public class AssetDAOImpl implements AssetDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAsset(AssetDTO assetDTO) {
        List<String> errors = new ArrayList<>();
        if (assetDTO.getCode() == null || assetDTO.getCode().trim().isEmpty()) {
            errors.add("Asset code is required.");
        } else if (assetDTO.getCode().length() > 50) {
            errors.add("Asset code must not exceed 50 characters.");
        } else if (isAssetCodeDuplicate(assetDTO.getCode(), assetDTO.getAssetId())) {
            errors.add("Asset code is already in use.");
        }
        if (assetDTO.getName() == null || !isValidName(assetDTO.getName())) {
            errors.add("Asset name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetDTO.getAssetPurchaseId() == null) {
            errors.add("Asset purchase ID is required.");
        }
        if (assetDTO.getType() == null) {
            errors.add("Asset type is required.");
        }
        if (assetDTO.getStatus() == null) {
            errors.add("Asset status is required.");
        }
        if (assetDTO.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (assetDTO.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetDTO.getBarcode() != null && assetDTO.getBarcode().length() > 50) {
            errors.add("Barcode must not exceed 50 characters.");
        }
        if (assetDTO.getDescription() != null && assetDTO.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        if (assetDTO.getCreateById() != null) {
            Employee createBy = entityManager.find(Employee.class, assetDTO.getCreateById());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetDTO.getCreateById());
            }
        }
        if (assetDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetDTO.getModifiedById());
            }
        }
        return errors;
    }

    @Override
    public AssetDTO createAsset(AssetDTO assetDTO) {
        try {
            Asset asset = assetDTO.toEntity();
            if (assetDTO.getCreateById() != null) {
                Employee createBy = entityManager.find(Employee.class, assetDTO.getCreateById());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetDTO.getCreateById());
                }
                asset.setCreateBy(createBy);
                asset.setModifiedBy(createBy);
            }
            entityManager.persist(asset);
            return AssetDTO.fromEntity(asset);
        } catch (Exception e) {
            logger.error("Failed to create asset with code {}: {}", assetDTO.getCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetDTO getAssetById(Integer id) {
        try {
            Asset asset = entityManager.find(Asset.class, id);
            if (asset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + id);
            }
            return AssetDTO.fromEntity(asset);
        } catch (Exception e) {
            logger.error("Failed to retrieve asset with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetDTO> getAllAssets() {
        try {
            TypedQuery<Asset> query = entityManager.createQuery("SELECT a FROM Asset a", Asset.class);
            return query.getResultList().stream()
                    .map(AssetDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all assets: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetDTO updateAsset(Integer id, AssetDTO assetDTO) {
        try {
            Asset existingAsset = entityManager.find(Asset.class, id);
            if (existingAsset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + id);
            }
            existingAsset.setCode(assetDTO.getCode());
            existingAsset.setName(assetDTO.getName());
            existingAsset.setAssetPurchaseId(assetDTO.getAssetPurchaseId());
            existingAsset.setBarcode(assetDTO.getBarcode());
            existingAsset.setType(assetDTO.getType());
            existingAsset.setStatus(assetDTO.getStatus());
            existingAsset.setIsUse(assetDTO.getIsUse());
            existingAsset.setIsActive(assetDTO.getIsActive());
            existingAsset.setDescription(assetDTO.getDescription());
            if (assetDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetDTO.getModifiedById());
                }
                existingAsset.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAsset);
            return AssetDTO.fromEntity(existingAsset);
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