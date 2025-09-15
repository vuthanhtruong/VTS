package com.example.demo.Employee.dao;

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
public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<String> validateEmployee(Employee employee) {
        List<String> errors = new ArrayList<>();
        if (employee.getEmployeeCode() == null || employee.getEmployeeCode().trim().isEmpty()) {
            errors.add("Employee code is required.");
        } else if (employee.getEmployeeCode().length() > 50) {
            errors.add("Employee code must not exceed 50 characters.");
        } else if (isEmployeeCodeDuplicate(employee.getEmployeeCode(), employee.getEmployeeId())) {
            errors.add("Employee code is already in use.");
        }
        if (employee.getEmployeeName() == null || !isValidName(employee.getEmployeeName())) {
            errors.add("Employee name is not valid. Only letters, spaces, and standard punctuation are allowed.");
        }
        if (employee.getEmployeeType() == null) {
            errors.add("Employee type is required.");
        }
        if (employee.getRankCode() == null || employee.getRankCode().trim().isEmpty()) {
            errors.add("Rank code is required.");
        } else if (employee.getRankCode().length() > 50) {
            errors.add("Rank code must not exceed 50 characters.");
        }
        if (employee.getEmail() != null && !isValidEmail(employee.getEmail())) {
            errors.add("Invalid email format.");
        }
        if (employee.getPhone() != null && !isValidPhoneNumber(employee.getPhone())) {
            errors.add("Invalid phone number format. Must be 10-15 digits, optionally starting with '+'.");
        }
        if (employee.getIsActive() == null) {
            errors.add("Is active status is required.");
        }
        if (employee.getCreatedBy() != null && employee.getCreatedBy().getEmployeeId() != null) {
            Employee createdBy = entityManager.find(Employee.class, employee.getCreatedBy().getEmployeeId());
            if (createdBy == null) {
                errors.add("Creator Employee not found with ID: " + employee.getCreatedBy().getEmployeeId());
            }
        }
        if (employee.getModifiedBy() != null && employee.getModifiedBy().getEmployeeId() != null) {
            Employee modifiedBy = entityManager.find(Employee.class, employee.getModifiedBy().getEmployeeId());
            if (modifiedBy == null) {
                errors.add("Modifier Employee not found with ID: " + employee.getModifiedBy().getEmployeeId());
            }
        }
        return errors;
    }

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee createEmployee(Employee employee) {
        try {
            if (employee.getCreatedBy() != null && employee.getCreatedBy().getEmployeeId() != null) {
                Employee createdBy = entityManager.find(Employee.class, employee.getCreatedBy().getEmployeeId());
                if (createdBy == null) {
                    throw new EntityNotFoundException("Creator Employee not found with ID: " + employee.getCreatedBy().getEmployeeId());
                }
                employee.setCreatedBy(createdBy);
                employee.setModifiedBy(createdBy);
            }
            entityManager.persist(employee);
            return employee;
        } catch (Exception e) {
            logger.error("Failed to create employee: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        try {
            Employee employee = entityManager.find(Employee.class, id);
            if (employee == null) {
                throw new EntityNotFoundException("Employee not found with ID: " + id);
            }
            return employee;
        } catch (Exception e) {
            logger.error("Failed to retrieve employee with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e", Employee.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Failed to retrieve all employees: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        try {
            Employee existingEmployee = entityManager.find(Employee.class, id);
            if (existingEmployee == null) {
                throw new EntityNotFoundException("Employee not found with ID: " + id);
            }
            existingEmployee.setEmployeeCode(employee.getEmployeeCode());
            existingEmployee.setEmployeeName(employee.getEmployeeName());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setEmployeeType(employee.getEmployeeType());
            existingEmployee.setRankCode(employee.getRankCode());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setIsActive(employee.getIsActive());
            if (employee.getModifiedBy() != null && employee.getModifiedBy().getEmployeeId() != null) {
                Employee modifiedBy = entityManager.find(Employee.class, employee.getModifiedBy().getEmployeeId());
                if (modifiedBy == null) {
                    throw new EntityNotFoundException("Modifier Employee not found with ID: " + employee.getModifiedBy().getEmployeeId());
                }
                existingEmployee.setModifiedBy(modifiedBy);
            }
            entityManager.merge(existingEmployee);
            return existingEmployee;
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