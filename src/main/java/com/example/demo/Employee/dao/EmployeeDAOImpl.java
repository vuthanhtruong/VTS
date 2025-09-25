package com.example.demo.Employee.dao;

import com.example.demo.Employee.dto.EmployeeDTO;
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
public class EmployeeDAOImpl implements EmployeeDAO {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> validateEmployee(EmployeeDTO employeeDTO) {
        List<String> errors = new ArrayList<>();
        if (employeeDTO.getEmployeeCode() == null || employeeDTO.getEmployeeCode().trim().isEmpty()) {
            errors.add("Employee code is required.");
        } else if (employeeDTO.getEmployeeCode().length() > 50) {
            errors.add("Employee code must not exceed 50 characters.");
        } else if (isEmployeeCodeDuplicate(employeeDTO.getEmployeeCode(), employeeDTO.getEmployeeId())) {
            errors.add("Employee code is already in use.");
        }
        if (employeeDTO.getEmployeeName() == null || !isValidName(employeeDTO.getEmployeeName())) {
            errors.add("Employee name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (employeeDTO.getEmployeeType() == null) {
            errors.add("Employee type is required.");
        }
        if (employeeDTO.getRankCode() == null || employeeDTO.getRankCode().trim().isEmpty()) {
            errors.add("Rank code is required.");
        } else if (employeeDTO.getRankCode().length() > 50) {
            errors.add("Rank code must not exceed 50 characters.");
        }
        if (employeeDTO.getEmail() != null && !isValidEmail(employeeDTO.getEmail())) {
            errors.add("Invalid email format.");
        }
        if (employeeDTO.getPhone() != null && !isValidPhoneNumber(employeeDTO.getPhone())) {
            errors.add("Invalid phone number format. Must be 10-15 digits, optionally starting with '+'.");
        }
        if (employeeDTO.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (employeeDTO.getCreatedById() != null) {
            Employee createdBy = entityManager.find(Employee.class, employeeDTO.getCreatedById());
            if (createdBy == null) {
                errors.add("Creator Employee not found with ID: " + employeeDTO.getCreatedById());
            }
        }
        if (employeeDTO.getModifiedById() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, employeeDTO.getModifiedById());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + employeeDTO.getModifiedById());
            }
        }
        return errors;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeDTO.toEntity();
            if (employeeDTO.getCreatedById() != null) {
                Employee createdBy = entityManager.find(Employee.class, employeeDTO.getCreatedById());
                if (createdBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + employeeDTO.getCreatedById());
                }
                employee.setCreatedBy(createdBy);
                employee.setModifiedBy(createdBy);
            }
            entityManager.persist(employee);
            return EmployeeDTO.fromEntity(employee);
        } catch (Exception e) {
            logger.error("Failed to create employee: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        try {
            Employee employee = entityManager.find(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException("Employee not found with ID: " + id);
            }
            return EmployeeDTO.fromEntity(employee);
        } catch (Exception e) {
            logger.error("Failed to retrieve employee with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
            return query.getResultList().stream()
                    .map(EmployeeDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to retrieve all employees: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        try {
            Employee existingEmployee = entityManager.find(Employee.class, id);
            if (existingEmployee == null) {
                throw new EntityNotFoundException("Employee not found with ID: " + id);
            }
            existingEmployee.setEmployeeCode(employeeDTO.getEmployeeCode());
            existingEmployee.setEmployeeName(employeeDTO.getEmployeeName());
            existingEmployee.setDepartment(employeeDTO.getDepartment());
            existingEmployee.setPosition(employeeDTO.getPosition());
            existingEmployee.setGender(employeeDTO.getGender());
            existingEmployee.setEmployeeType(employeeDTO.getEmployeeType());
            existingEmployee.setRankCode(employeeDTO.getRankCode());
            existingEmployee.setPhone(employeeDTO.getPhone());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setIsActive(employeeDTO.getIsActive());
            if (employeeDTO.getModifiedById() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, employeeDTO.getModifiedById());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + employeeDTO.getModifiedById());
                }
                existingEmployee.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingEmployee);
            return EmployeeDTO.fromEntity(existingEmployee);
        } catch (Exception e) {
            logger.error("Failed to update employee with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteEmployee(Integer id) {
        try {
            Employee employee = entityManager.find(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException("Employee not found with ID: " + id);
            }
            entityManager.remove(employee);
        } catch (Exception e) {
            logger.error("Failed to delete employee with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    private boolean isEmployeeCodeDuplicate(String employeeCode, Integer excludeId) {
        String jpql = "SELECT COUNT(e) FROM Employee e WHERE e.employeeCode = :code";
        if (excludeId != null) {
            jpql += " AND e.employeeId != :excludeId";
        }
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("code", employeeCode);
        if (excludeId != null) {
            query.setParameter("excludeId", excludeId);
        }
        return query.getSingleResult() > 0;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Email is optional
        }
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true; // Phone is optional
        }
        String phoneRegex = "^\\+?[1-9][0-9]{7,14}$";
        return phoneNumber.matches(phoneRegex);
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String nameRegex = "^(?=.{2,255}$)(\\p{L}+[\\p{L}'’\\-\\.]*)((\\s+\\p{L}+[\\p{L}'’\\-\\.]*)*)$";
        return name.matches(nameRegex);
    }
}