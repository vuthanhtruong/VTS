package com.example.demo.AssetFixedDecrease.dao;

import com.example.demo.AssetFixed.model.AssetFixed;
import com.example.demo.AssetFixedDecrease.dto.AssetFixedDecreaseDTO;
import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;
import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import com.example.demo.Employee.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class AssetFixedDecreaseDAOImpl implements AssetFixedDecreaseDAO {

    private static final Logger logger = LoggerFactory.getLogger(AssetFixedDecreaseDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        List<String> errors = new ArrayList<>();
        if (assetFixedDecreaseDTO.getCode() == null || assetFixedDecreaseDTO.getCode().trim().isEmpty()) {
            errors.add("Asset fixed decrease code is required.");
        } else if (assetFixedDecreaseDTO.getCode().length() > 50) {
            errors.add("Asset fixed decrease code must not exceed 50 characters.");
        } else if (isAssetFixedDecreaseCodeDuplicate(assetFixedDecreaseDTO.getCode(), assetFixedDecreaseDTO.getAssetFixedDecreaseId())) {
            errors.add("Asset fixed decrease code is already in use.");
        }
        if (assetFixedDecreaseDTO.getName() == null || !isValidName(assetFixedDecreaseDTO.getName())) {
            errors.add("Asset fixed decrease name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (assetFixedDecreaseDTO.getAssetFixedId() != null) {
            AssetFixed assetFixed = entityManager.find(AssetFixed.class, assetFixedDecreaseDTO.getAssetFixedId());
            if (assetFixed == null) {
                errors.add("Asset fixed not found with ID: " + assetFixedDecreaseDTO.getAssetFixedId());
            }
        }
        if (assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId() == null) {
            errors.add("Asset fixed decrease reason is required.");
        } else {
            AssetFixedDecreaseReason reason = entityManager.find(AssetFixedDecreaseReason.class, assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId());
            if (reason == null) {
                errors.add("Asset fixed decrease reason not found with ID: " + assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId());
            }
        }
        if (assetFixedDecreaseDTO.getVoucherNo() != null && assetFixedDecreaseDTO.getVoucherNo().length() > 50) {
            errors.add("Voucher number must not exceed 50 characters.");
        }
        if (assetFixedDecreaseDTO.getInvoiceNo() != null && assetFixedDecreaseDTO.getInvoiceNo().length() > 50) {
            errors.add("Invoice number must not exceed 50 characters.");
        }
        if (assetFixedDecreaseDTO.getType() == null) {
            errors.add("Type is required.");
        }
        if (assetFixedDecreaseDTO.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (assetFixedDecreaseDTO.getCreateById() != null) {
            Employee createBy = entityManager.find(Employee.class, assetFixedDecreaseDTO.getCreateById());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + assetFixedDecreaseDTO.getCreateById());
            }
        }
        if (assetFixedDecreaseDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, assetFixedDecreaseDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + assetFixedDecreaseDTO.getModifiedById());
            }
        }
        if (assetFixedDecreaseDTO.getVoucherUserId() != null) {
            Employee voucherUser = entityManager.find(Employee.class, assetFixedDecreaseDTO.getVoucherUserId());
            if (voucherUser == null) {
                errors.add("Voucher user not found with ID: " + assetFixedDecreaseDTO.getVoucherUserId());
            }
        }
        if (assetFixedDecreaseDTO.getDescription() != null && assetFixedDecreaseDTO.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }

        // Additional validation for numeric fields to ensure non-negative
        if (assetFixedDecreaseDTO.getDecRev() != null && assetFixedDecreaseDTO.getDecRev() < 0) {
            errors.add("Revenue from decrease must be non-negative.");
        }
        if (assetFixedDecreaseDTO.getDepAccUnpaid() != null && assetFixedDecreaseDTO.getDepAccUnpaid() < 0) {
            errors.add("Unpaid deposit account must be non-negative.");
        }
        if (assetFixedDecreaseDTO.getDepAccPaid() != null && assetFixedDecreaseDTO.getDepAccPaid() < 0) {
            errors.add("Paid deposit account must be non-negative.");
        }
        if (assetFixedDecreaseDTO.getAssetProcCost() != null && assetFixedDecreaseDTO.getAssetProcCost() < 0) {
            errors.add("Asset processing cost must be non-negative.");
        }

        return errors;
    }

    @Override
    public AssetFixedDecreaseDTO createAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        try {
            // Validate trước khi tạo
            List<String> validationErrors = validateAssetFixedDecrease(assetFixedDecreaseDTO);
            if (!validationErrors.isEmpty()) {
                throw new IllegalArgumentException("Validation failed: " + String.join(", ", validationErrors));
            }

            // Chuyển đổi DTO sang Entity
            AssetFixedDecrease assetFixedDecrease = assetFixedDecreaseDTO.toEntity();

            // Ánh xạ assetFixed
            if (assetFixedDecreaseDTO.getAssetFixedId() != null) {
                AssetFixed assetFixed = entityManager.find(AssetFixed.class, assetFixedDecreaseDTO.getAssetFixedId());
                if (assetFixed == null) {
                    throw new EntityNotFoundException("Asset fixed not found with ID: " + assetFixedDecreaseDTO.getAssetFixedId());
                }
                assetFixedDecrease.setAssetFixed(assetFixed);
            } else {
                throw new EntityNotFoundException("Asset fixed ID is required.");
            }

            // Ánh xạ assetFixedType (nếu có)
            if (assetFixedDecreaseDTO.getAssetFixedTypeId() != null) {
                AssetFixed assetFixedType = entityManager.find(AssetFixed.class, assetFixedDecreaseDTO.getAssetFixedTypeId());
                if (assetFixedType == null) {
                    throw new EntityNotFoundException("Asset fixed type not found with ID: " + assetFixedDecreaseDTO.getAssetFixedTypeId());
                }
                assetFixedDecrease.setAssetFixedType(assetFixedType);
            }

            // Ánh xạ assetFixedDecreaseReason (bắt buộc)
            if (assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId() == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason is required.");
            }
            AssetFixedDecreaseReason reason = entityManager.find(AssetFixedDecreaseReason.class, assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId());
            if (reason == null) {
                throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId());
            }
            assetFixedDecrease.setAssetFixedDecreaseReason(reason);

            // Ánh xạ createBy và modifiedBy (bắt buộc nếu có ID)
            if (assetFixedDecreaseDTO.getCreateById() != null) {
                Employee createBy = entityManager.find(Employee.class, assetFixedDecreaseDTO.getCreateById());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + assetFixedDecreaseDTO.getCreateById());
                }
                assetFixedDecrease.setCreateBy(createBy);
                assetFixedDecrease.setModifiedBy(createBy);
            } else {
                throw new EntityNotFoundException("CreateBy ID is required.");
            }

            // Ánh xạ voucherUser
            if (assetFixedDecreaseDTO.getVoucherUserId() != null) {
                Employee voucherUser = entityManager.find(Employee.class, assetFixedDecreaseDTO.getVoucherUserId());
                if (voucherUser == null) {
                    throw new EntityNotFoundException("Voucher user not found with ID: " + assetFixedDecreaseDTO.getVoucherUserId());
                }
                assetFixedDecrease.setVoucherUser(voucherUser);
            }

            // Ánh xạ các trường số (decRev, depAccUnpaid, depAccPaid, assetProcCost)
            assetFixedDecrease.setDecRev(assetFixedDecreaseDTO.getDecRev());
            assetFixedDecrease.setDepAccUnpaid(assetFixedDecreaseDTO.getDepAccUnpaid());
            assetFixedDecrease.setDepAccPaid(assetFixedDecreaseDTO.getDepAccPaid());
            assetFixedDecrease.setAssetProcCost(assetFixedDecreaseDTO.getAssetProcCost());

            // Đảm bảo createDate và modifiedDate không bị ghi đè từ DTO
            assetFixedDecrease.setCreateDate(null); // Sẽ được set bởi @PrePersist
            assetFixedDecrease.setModifiedDate(null); // Sẽ được set bởi @PreUpdate

            // Lưu và flush để đảm bảo dữ liệu được commit
            entityManager.persist(assetFixedDecrease);
            entityManager.flush();

            // Trả về DTO
            return AssetFixedDecreaseDTO.fromEntity(assetFixedDecrease);
        } catch (IllegalArgumentException e) {
            logger.error("Validation failed for asset fixed decrease with code {}: {}", assetFixedDecreaseDTO.getCode(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (EntityNotFoundException e) {
            logger.error("Entity not found for asset fixed decrease with code {}: {}", assetFixedDecreaseDTO.getCode(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to create asset fixed decrease with code {}: {}", assetFixedDecreaseDTO.getCode(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create asset fixed decrease", e);
        }
    }

    @Override
    public AssetFixedDecreaseDTO getAssetFixedDecreaseById(Integer id) {
        try {
            AssetFixedDecrease assetFixedDecrease = entityManager.find(AssetFixedDecrease.class, id);
            if (assetFixedDecrease == null) {
                throw new EntityNotFoundException("Asset fixed decrease not found with ID: " + id);
            }
            return AssetFixedDecreaseDTO.fromEntity(assetFixedDecrease);
        } catch (Exception e) {
            logger.error("Failed to retrieve asset fixed decrease with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedDecreaseDTO> getAllAssetFixedDecreases() {
        try {
            TypedQuery<AssetFixedDecrease> query = entityManager.createQuery("SELECT afd FROM AssetFixedDecrease afd", AssetFixedDecrease.class);
            return query.getResultList().stream()
                    .map(AssetFixedDecreaseDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all asset fixed decreases: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<AssetFixedDecreaseDTO> getPaginatedAssetFixedDecreases(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be non-negative and size must be positive.");
        }
        try {
            TypedQuery<AssetFixedDecrease> query = entityManager.createQuery(
                    "SELECT afd FROM AssetFixedDecrease afd ORDER BY afd.assetFixedDecreaseId DESC",
                    AssetFixedDecrease.class
            );
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            return query.getResultList().stream()
                    .map(AssetFixedDecreaseDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve paginated asset fixed decreases for page {} and size {}: {}",
                    page, size, e.getMessage());
            throw e;
        }
    }

    @Override
    public long countAssetFixedDecreases() {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(afd) FROM AssetFixedDecrease afd",
                    Long.class
            );
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Failed to count asset fixed decreases: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssetFixedDecreaseDTO updateAssetFixedDecrease(Integer id, AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        try {
            AssetFixedDecrease existingAssetFixedDecrease = entityManager.find(AssetFixedDecrease.class, id);
            if (existingAssetFixedDecrease == null) {
                throw new EntityNotFoundException("Asset fixed decrease not found with ID: " + id);
            }
            existingAssetFixedDecrease.setCode(assetFixedDecreaseDTO.getCode());
            existingAssetFixedDecrease.setName(assetFixedDecreaseDTO.getName());
            if (assetFixedDecreaseDTO.getAssetFixedId() != null) {
                AssetFixed assetFixed = entityManager.find(AssetFixed.class, assetFixedDecreaseDTO.getAssetFixedId());
                if (assetFixed == null) {
                    throw new EntityNotFoundException("Asset fixed not found with ID: " + assetFixedDecreaseDTO.getAssetFixedId());
                }
                existingAssetFixedDecrease.setAssetFixed(assetFixed);
            } else {
                existingAssetFixedDecrease.setAssetFixed(null);
            }
            if (assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId() != null) {
                AssetFixedDecreaseReason reason = entityManager.find(AssetFixedDecreaseReason.class, assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId());
                if (reason == null) {
                    throw new EntityNotFoundException("Asset fixed decrease reason not found with ID: " + assetFixedDecreaseDTO.getAssetFixedDecreaseReasonId());
                }
                existingAssetFixedDecrease.setAssetFixedDecreaseReason(reason);
            }
            existingAssetFixedDecrease.setVoucherNo(assetFixedDecreaseDTO.getVoucherNo());
            existingAssetFixedDecrease.setVoucherDate(assetFixedDecreaseDTO.getVoucherDate());
            if (assetFixedDecreaseDTO.getVoucherUserId() != null) {
                Employee voucherUser = entityManager.find(Employee.class, assetFixedDecreaseDTO.getVoucherUserId());
                if (voucherUser == null) {
                    throw new EntityNotFoundException("Voucher user not found with ID: " + assetFixedDecreaseDTO.getVoucherUserId());
                }
                existingAssetFixedDecrease.setVoucherUser(voucherUser);
            } else {
                existingAssetFixedDecrease.setVoucherUser(null);
            }
            existingAssetFixedDecrease.setType(assetFixedDecreaseDTO.getType());
            existingAssetFixedDecrease.setInvoiceNo(assetFixedDecreaseDTO.getInvoiceNo());
            existingAssetFixedDecrease.setInvoiceDate(assetFixedDecreaseDTO.getInvoiceDate());
            existingAssetFixedDecrease.setDecRev(assetFixedDecreaseDTO.getDecRev());
            existingAssetFixedDecrease.setDepAccUnpaid(assetFixedDecreaseDTO.getDepAccUnpaid());
            existingAssetFixedDecrease.setDepAccPaid(assetFixedDecreaseDTO.getDepAccPaid());
            existingAssetFixedDecrease.setAssetProcCost(assetFixedDecreaseDTO.getAssetProcCost());
            existingAssetFixedDecrease.setIsActive(assetFixedDecreaseDTO.getIsActive());
            existingAssetFixedDecrease.setDescription(assetFixedDecreaseDTO.getDescription());
            if (assetFixedDecreaseDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, assetFixedDecreaseDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + assetFixedDecreaseDTO.getModifiedById());
                }
                existingAssetFixedDecrease.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingAssetFixedDecrease);
            return AssetFixedDecreaseDTO.fromEntity(existingAssetFixedDecrease);
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