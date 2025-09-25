package com.example.demo.Employee.dto;

import com.example.demo.Employee.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    private Integer employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String position;
    private String gender;
    private Integer employeeType;
    private String rankCode;
    private String phone;
    private String email;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer createdById;
    private Integer modifiedById;

    public EmployeeDTO() {}

    public EmployeeDTO(Integer employeeId, String employeeCode, String employeeName, String department,
                       String position, String gender, Integer employeeType, String rankCode,
                       String phone, String email, Boolean isActive, LocalDateTime createdDate,
                       LocalDateTime modifiedDate, Integer createdById, Integer modifiedById) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.department = department;
        this.position = position;
        this.gender = gender;
        this.employeeType = employeeType;
        this.rankCode = rankCode;
        this.phone = phone;
        this.email = email;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.createdById = createdById;
        this.modifiedById = modifiedById;
    }

    // Map Entity to DTO
    public static EmployeeDTO fromEntity(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getEmployeeCode(),
                employee.getEmployeeName(),
                employee.getDepartment(),
                employee.getPosition(),
                employee.getGender(),
                employee.getEmployeeType(),
                employee.getRankCode(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getIsActive(),
                employee.getCreatedDate(),
                employee.getModifiedDate(),
                employee.getCreatedBy() != null ? employee.getCreatedBy().getEmployeeId() : null,
                employee.getModifiedBy() != null ? employee.getModifiedBy().getEmployeeId() : null
        );
    }

    // Map DTO to Entity
    public Employee toEntity() {
        Employee employee = new Employee();
        employee.setEmployeeId(this.employeeId);
        employee.setEmployeeCode(this.employeeCode);
        employee.setEmployeeName(this.employeeName);
        employee.setDepartment(this.department);
        employee.setPosition(this.position);
        employee.setGender(this.gender);
        employee.setEmployeeType(this.employeeType);
        employee.setRankCode(this.rankCode);
        employee.setPhone(this.phone);
        employee.setEmail(this.email);
        employee.setIsActive(this.isActive);
        employee.setCreatedDate(this.createdDate);
        employee.setModifiedDate(this.modifiedDate);
        // Note: createdBy and modifiedBy are not set here as they require fetching Employee entities
        return employee;
    }
}