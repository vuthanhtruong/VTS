package com.example.demo.AssetFixedType.dao;

import com.example.demo.AssetFixedType.dto.AssetFixedTypeDTO;
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
public class AssetFixedTypeDAOImpl implements AssetFixedTypeDAO {
    private static final Logger logger = LoggerFactory.getLogger(AssetFixedTypeDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO) {
        List<String> errors = new ArrayList<>();
        if (assetFixedTypeDTO.getCode() == null || assetFixedTypeDTO.getCode().trim().isEmpty()) {
            errors.add("Asset fixed type code is required.");
        } else if (assetFixedTypeDTO.getCode().length() > 50) {
            errors.add("Asset fixed type code must not exceed 50 characters.");
        } else if (isAssetFixedTypeCodeDuplicate(assetFixedTypeDTO.getCode(), assetFixedTypeDTO.getAssetFixedTypeId())) {
            errors.add("Asset fixed type code is already in use.");
        }
        if (assetFixedTypeDTO.getName() == null || !isValidName(assetFixedTypeDTO.getName())) {
            errors.add("Asset fixed type name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixedTypeDTO.getOldDepreciationRate() == null) {
            errors.add("Old depreciation rate is required.");
        }
        if (assetFixedTypeDTO.getNewDepreciationRate() == null) {
            errors.add("New depreciation rate is required.");
        }
        if (assetFixedTypeDTO.getYearApplication() == null) {
            errors.add("Year application is required.");
        }
        if (assetFixedTypeDTO.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (assetFixedTypeDTO.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixedTypeDTO.getParentId() != null) {
            AssetFixedType parent = entityManager.find(AssetFixedType.class, assetFixedTypeDTO.getParentId());
            if (parent == null) {
                errors.add("Parent asset fixed type not found with ID: " + assetFixedTypeDTO.getParentId());
            }
        }
        if (assetFixedTypeDTO.getCreateById() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixedTypeDTO.getCreateById());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixedTypeDTO.getCreateById());
            }
        }
        if (assetFixedTypeDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixedTypeDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixedTypeDTO.getModifiedById());
            }
        }
        if (assetFixedTypeDTO.getDescription() != null && assetFixedTypeDTO.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        return errors;
    }

    @Override
    public AssetFixedTypeDTO createAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO) {
        try {
            AssetFixedType assetFixedType = assetFixedTypeDTO.toEntity();
            if (assetFixedTypeDTO.getParentId() != null) {
                AssetFixedType parent = entityManager.find(AssetFixedType.class, assetFixedTypeDTO.getParentId());
                if (parent == null) {
                    throw new EntityNotFoundException("Parent asset fixed type not found with ID: " + assetFixedTypeDTO.getParentId());
                }
                assetFixedType.setAssetFixedTypeParent(parent);
            }
            if (assetFixedTypeDTO.getCreateById() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixedTypeDTO.getCreateById());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixedTypeDTO.getCreateById());
                }
                assetFixedType.setCreateBy(createBy);
                assetFixedType.setModifiedBy(createBy);
            }
            entityManager.persist(assetFixedType);
            return AssetFixedTypeDTO.fromEntity(assetFixedType);
        } catch (Exception e) {
            logger.error("Failed to create asset fixed type with code {}: {}", assetFixedTypeDTO.getCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedTypeDTO getAssetFixedTypeById(Integer id) {
        try {
            AssetFixedType assetFixedType = entityManager.find(AssetFixedType.class, id);
            if (assetFixedType == null) {
                throw new EntityNotFoundException("Asset fixed type not found with ID: " + id);
            }
            return AssetFixedTypeDTO.fromEntity(assetFixedType);
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedTypeDTO> getAllAssetFixedTypes() {
        try {
            TypedQuery<AssetFixedType> query = entityManager.createQuery("SELECT aft FROM AssetFixedType aft", AssetFixedType.class);
            return query.getResultList().stream()
                    .map(AssetFixedTypeDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixed types: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedTypeDTO updateAssetFixedType(Integer id, AssetFixedTypeDTO assetFixedTypeDTO) {
        try {
            AssetFixedType existingAssetFixedType = entityManager.find(AssetFixedType.class, id);
            if (existingAssetFixedType == null) {
                throw new EntityNotFoundException("Asset fixed type not found with ID: " + id);
            }
            existingAssetFixedType.setCode(assetFixedTypeDTO.getCode());
            existingAssetFixedType.setName(assetFixedTypeDTO.getName());
            existingAssetFixedType.setLastYearUseTime(assetFixedTypeDTO.getLastYearUseTime());
            existingAssetFixedType.setNewYearUseTime(assetFixedTypeDTO.getNewYearUseTime());
            existingAssetFixedType.setOldDepreciationRate(assetFixedTypeDTO.getOldDepreciationRate());
            existingAssetFixedType.setNewDepreciationRate(assetFixedTypeDTO.getNewDepreciationRate());
            existingAssetFixedType.setYearApplication(assetFixedTypeDTO.getYearApplication());
            existingAssetFixedType.setIsUse(assetFixedTypeDTO.getIsUse());
            existingAssetFixedType.setIsActive(assetFixedTypeDTO.getIsActive());
            existingAssetFixedType.setDescription(assetFixedTypeDTO.getDescription());
            if (assetFixedTypeDTO.getParentId() != null) {
                AssetFixedType parent = entityManager.find(AssetFixedType.class, assetFixedTypeDTO.getParentId());
                if (parent == null) {
                    throw new EntityNotFoundException("Parent asset fixed type not found with ID: " + assetFixedTypeDTO.getParentId());
                }
                existingAssetFixedType.setAssetFixedTypeParent(parent);
            } else {
                existingAssetFixedType.setAssetFixedTypeParent(null);
            }
            if (assetFixedTypeDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixedTypeDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixedTypeDTO.getModifiedById());
                }
                existingAssetFixedType.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixedType);
            return AssetFixedTypeDTO.fromEntity(existingAssetFixedType);
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