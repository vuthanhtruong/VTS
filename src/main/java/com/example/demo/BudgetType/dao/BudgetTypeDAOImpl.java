package com.example.demo.BudgetType.dao;

import com.example.demo.BudgetType.dto.BudgetTypeDTO;
import com.example.demo.BudgetType.model.BudgetType;
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
public class BudgetTypeDAOImpl implements BudgetTypeDAO {

    private static final Logger logger = LoggerFactory.getLogger(BudgetTypeDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateBudgetType(BudgetTypeDTO budgetTypeDTO) {
        List<String> errors = new ArrayList<>();
        if (budgetTypeDTO.getBudgetTypeCode() == null || budgetTypeDTO.getBudgetTypeCode().trim().isEmpty()) {
            errors.add("Budget type code is required.");
        } else if (budgetTypeDTO.getBudgetTypeCode().length() > 50) {
            errors.add("Budget type code must not exceed 50 characters.");
        } else if (isBudgetTypeCodeDuplicate(budgetTypeDTO.getBudgetTypeCode(), budgetTypeDTO.getBudgetTypeId())) {
            errors.add("Budget type code is already in use.");
        }
        if (budgetTypeDTO.getBudgetTypeName() == null || budgetTypeDTO.getBudgetTypeName().trim().isEmpty()) {
            errors.add("Budget type name is required.");
        } else if (budgetTypeDTO.getBudgetTypeName().length() > 255) {
            errors.add("Budget type name must not exceed 255 characters.");
        } else if (!isValidName(budgetTypeDTO.getBudgetTypeName())) {
            errors.add("Budget type name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (budgetTypeDTO.getIsUse() == null) {
            errors.add("Is use status is required.");
        }
        if (budgetTypeDTO.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (budgetTypeDTO.getBudgetTypeParentId() != null) {
            BudgetType parent = entityManager.find(BudgetType.class, budgetTypeDTO.getBudgetTypeParentId());
            if (parent == null) {
                errors.add("Parent budget type not found with ID: " + budgetTypeDTO.getBudgetTypeParentId());
            } else if (budgetTypeDTO.getBudgetTypeId() != null && budgetTypeDTO.getBudgetTypeId().equals(budgetTypeDTO.getBudgetTypeParentId())) {
                errors.add("Budget type cannot be its own parent.");
            }
        }
        if (budgetTypeDTO.getCreateById() != null) {
            Employee createBy = entityManager.find(Employee.class, budgetTypeDTO.getCreateById());
            if (createBy == null) {
                errors.add("Creator Employee not found with ID: " + budgetTypeDTO.getCreateById());
            }
        }
        if (budgetTypeDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, budgetTypeDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + budgetTypeDTO.getModifiedById());
            }
        }
        if (budgetTypeDTO.getDescription() != null && budgetTypeDTO.getDescription().length() > 255) {
            errors.add("Description must not exceed 255 characters.");
        }
        return errors;
    }

    @Override
    public BudgetTypeDTO createBudgetType(BudgetTypeDTO budgetTypeDTO) {
        try {
            BudgetType budgetType = budgetTypeDTO.toEntity();
            if (budgetTypeDTO.getBudgetTypeParentId() != null) {
                BudgetType parent = entityManager.find(BudgetType.class, budgetTypeDTO.getBudgetTypeParentId());
                if (parent == null) {
                    throw new EntityNotFoundException("Parent budget type not found with ID: " + budgetTypeDTO.getBudgetTypeParentId());
                }
                budgetType.setBudgetTypeParent(parent);
            }
            if (budgetTypeDTO.getCreateById() != null) {
                Employee createBy = entityManager.find(Employee.class, budgetTypeDTO.getCreateById());
                if (createBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + budgetTypeDTO.getCreateById());
                }
                budgetType.setCreateBy(createBy);
                budgetType.setModifiedBy(createBy);
            }
            entityManager.persist(budgetType);
            return BudgetTypeDTO.fromEntity(budgetType);
        } catch (Exception e) {
            logger.error("Failed to create budget type with code {}: {}", budgetTypeDTO.getBudgetTypeCode(), e.getMessage());
            throw e;
        }
    }

    @Override
    public BudgetTypeDTO getBudgetTypeById(Integer id) {
        try {
            BudgetType budgetType = entityManager.find(BudgetType.class, id);
            if (budgetType == null) {
                throw new EntityNotFoundException("Budget type not found with ID: " + id);
            }
            return BudgetTypeDTO.fromEntity(budgetType);
        } catch (Exception e) {
            logger.error("Failed to retrieve budget type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BudgetTypeDTO> getAllBudgetTypes() {
        try {
            TypedQuery<BudgetType> query = entityManager.createQuery("SELECT bt FROM BudgetType bt", BudgetType.class);
            return query.getResultList().stream()
                    .map(BudgetTypeDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all budget types: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public BudgetTypeDTO updateBudgetType(Integer id, BudgetTypeDTO budgetTypeDTO) {
        try {
            BudgetType existingBudgetType = entityManager.find(BudgetType.class, id);
            if (existingBudgetType == null) {
                throw new EntityNotFoundException("Budget type not found with ID: " + id);
            }
            existingBudgetType.setBudgetTypeCode(budgetTypeDTO.getBudgetTypeCode());
            existingBudgetType.setBudgetTypeName(budgetTypeDTO.getBudgetTypeName());
            if (budgetTypeDTO.getBudgetTypeParentId() != null) {
                BudgetType parent = entityManager.find(BudgetType.class, budgetTypeDTO.getBudgetTypeParentId());
                if (parent == null) {
                    throw new EntityNotFoundException("Parent budget type not found with ID: " + budgetTypeDTO.getBudgetTypeParentId());
                }
                if (id.equals(budgetTypeDTO.getBudgetTypeParentId())) {
                    throw new IllegalArgumentException("Budget type cannot be its own parent.");
                }
                existingBudgetType.setBudgetTypeParent(parent);
            } else {
                existingBudgetType.setBudgetTypeParent(null);
            }
            existingBudgetType.setIsUse(budgetTypeDTO.getIsUse());
            existingBudgetType.setIsActive(budgetTypeDTO.getIsActive());
            existingBudgetType.setCreateDate(budgetTypeDTO.getCreateDate());
            existingBudgetType.setModifiedDate(budgetTypeDTO.getModifiedDate());
            existingBudgetType.setDescription(budgetTypeDTO.getDescription());
            if (budgetTypeDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, budgetTypeDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + budgetTypeDTO.getModifiedById());
                }
                existingBudgetType.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingBudgetType);
            return BudgetTypeDTO.fromEntity(existingBudgetType);
        } catch (Exception e) {
            logger.error("Failed to update budget type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteBudgetType(Integer id) {
        try {
            BudgetType budgetType = entityManager.find(BudgetType.class, id);
            if (budgetType == null) {
                throw new EntityNotFoundException("Budget type not found with ID: " + id);
            }
            entityManager.remove(budgetType);
        } catch (Exception e) {
            logger.error("Failed to delete budget type with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isBudgetTypeCodeDuplicate(String code, Integer excludeId) {
        String jpql = "SELECT COUNT(bt) FROM BudgetType bt WHERE bt.budgetTypeCode = :code";
        if (excludeId != null) {
            jpql += " AND bt.budgetTypeId != :excludeId";
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