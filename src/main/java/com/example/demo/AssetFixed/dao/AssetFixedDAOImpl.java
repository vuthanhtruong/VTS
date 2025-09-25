package com.example.demo.AssetFixed.dao;

import com.example.demo.Asset.model.Asset;
import com.example.demo.AssetFixed.dto.AssetFixedDTO;
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
import java.util.stream.Collectors;

@Repository
@Transactional
public class AssetFixedDAOImpl implements AssetFixedDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetFixedDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixed(AssetFixedDTO assetFixedDTO) {
        List<String> errors = new ArrayList<>();
        if (assetFixedDTO.getCode() == null || assetFixedDTO.getCode().trim().isEmpty()) {
            errors.add("Asset fixed code is required.");
        } else if (assetFixedDTO.getCode().length() > 50) {
            errors.add("Asset fixed code must not exceed 50 characters.");
        } else if (isAssetFixedCodeDuplicate(assetFixedDTO.getCode(), assetFixedDTO.getAssetFixedId())) {
            errors.add("Asset fixed code is already in use.");
        }
        if (assetFixedDTO.getName() == null || !isValidName(assetFixedDTO.getName())) {
            errors.add("Asset fixed name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixedDTO.getAssetId() == null) {
            errors.add("Asset ID is required.");
        } else {
            Asset asset = entityManager.find(Asset.class, assetFixedDTO.getAssetId());
            if (asset == null) {
                errors.add("Asset not found with ID: " + assetFixedDTO.getAssetId());
            }
        }
        if (assetFixedDTO.getAssetFixedTypeId() != null) {
            AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, assetFixedDTO.getAssetFixedTypeId());
            if (assetFixedType == null) {
                errors.add("Asset fixed type not found with ID: " + assetFixedDTO.getAssetFixedTypeId());
            }
        }
        if (assetFixedDTO.getUnit() != null && assetFixedDTO.getUnit().length() > 50) {
            errors.add("Unit must not exceed 50 characters.");
        }
        if (assetFixedDTO.getAssetFixedCard() != null && assetFixedDTO.getAssetFixedCard().length() > 50) {
            errors.add("Asset fixed card must not exceed 50 characters.");
        }
        if (assetFixedDTO.getDescription() != null && assetFixedDTO.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        if (assetFixedDTO.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (assetFixedDTO.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixedDTO.getCreateById() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixedDTO.getCreateById());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixedDTO.getCreateById());
            }
        }
        if (assetFixedDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixedDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixedDTO.getModifiedById());
            }
        }
        return errors;
    }

    @Override
    public AssetFixedDTO createAssetFixed(AssetFixedDTO assetFixedDTO) {
        try {
            AssetFixed assetFixed = assetFixedDTO.toEntity();
            if (assetFixedDTO.getAssetId() == null) {
                throw new EntityNotFoundException("Asset ID is required.");
            }
            Asset asset = entityManager.find(Asset.class, assetFixedDTO.getAssetId());
            if (asset == null) {
                throw new EntityNotFoundException("Asset not found with ID: " + assetFixedDTO.getAssetId());
            }
            assetFixed.setAsset(asset);
            if (assetFixedDTO.getAssetFixedTypeId() != null) {
                AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, assetFixedDTO.getAssetFixedTypeId());
                if (assetFixedType == null) {
                    throw new EntityNotFoundException("Asset fixed type not found with ID: " + assetFixedDTO.getAssetFixedTypeId());
                }
                assetFixed.setAssetFixedType(assetFixedType);
            }
            if (assetFixedDTO.getCreateById() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixedDTO.getCreateById());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixedDTO.getCreateById());
                }
                assetFixed.setCreateBy(createBy);
                assetFixed.setModifiedBy(createBy);
            }
            entityManager.persist(assetFixed);
            return AssetFixedDTO.fromEntity(assetFixed);
        } catch (Exception e) {
            logger.error("Failed to create asset fixed with code {}: {}", assetFixedDTO.getCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDTO getAssetFixedById(Integer id) {
        try {
            AssetFixed assetFixed = entityManager.find(AssetFixed.class, id);
            if (assetFixed == null) {
                throw new EntityNotFoundException("Asset fixed not found with ID: " + id);
            }
            return AssetFixedDTO.fromEntity(assetFixed);
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedDTO> getAllAssetFixed() {
        try {
            TypedQuery<AssetFixed> query = entityManager.createQuery("SELECT af FROM AssetFixed af", AssetFixed.class);
            return query.getResultList().stream()
                    .map(AssetFixedDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixeds: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDTO updateAssetFixed(Integer id, AssetFixedDTO assetFixedDTO) {
        try {
            AssetFixed existingAssetFixed = entityManager.find(AssetFixed.class, id);
            if (existingAssetFixed == null) {
                throw new EntityNotFoundException("Asset fixed not found with ID: " + id);
            }
            existingAssetFixed.setCode(assetFixedDTO.getCode());
            existingAssetFixed.setName(assetFixedDTO.getName());
            if (assetFixedDTO.getAssetId() != null) {
                Asset asset = entityManager.find(Asset.class, assetFixedDTO.getAssetId());
                if (asset == null) {
                    throw new EntityNotFoundException("Asset not found with ID: " + assetFixedDTO.getAssetId());
                }
                existingAssetFixed.setAsset(asset);
            }
            if (assetFixedDTO.getAssetFixedTypeId() != null) {
                AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, assetFixedDTO.getAssetFixedTypeId());
                if (assetFixedType == null) {
                    throw new EntityNotFoundException("Asset fixed type not found with ID: " + assetFixedDTO.getAssetFixedTypeId());
                }
                existingAssetFixed.setAssetFixedType(assetFixedType);
            } else {
                existingAssetFixed.setAssetFixedType(null);
            }
            existingAssetFixed.setUnit(assetFixedDTO.getUnit());
            existingAssetFixed.setAssetFixedCard(assetFixedDTO.getAssetFixedCard());
            existingAssetFixed.setIsUse(assetFixedDTO.getIsUse());
            existingAssetFixed.setIsActive(assetFixedDTO.getIsActive());
            existingAssetFixed.setDescription(assetFixedDTO.getDescription());
            if (assetFixedDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixedDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixedDTO.getModifiedById());
                }
                existingAssetFixed.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixed);
            return AssetFixedDTO.fromEntity(existingAssetFixed);
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