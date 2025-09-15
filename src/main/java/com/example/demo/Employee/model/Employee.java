package com.example.demo.Employee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Employees")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;

    @Column(name = "EMPLOYEE_CODE", nullable = false, length = 50)
    private String employeeCode;

    @Column(name = "EMPLOYEE_NAME", nullable = false, length = 255)
    private String employeeName;

    @Column(name = "DEPARTMENT", length = 255)
    private String department;

    @Column(name = "POSITION", length = 255)
    private String position;

    @Column(name = "GENDER", length = 50)
    private String gender;

    @Column(name = "EMPLOYEE_TYPE", nullable = false)
    private Integer employeeType;

    @Column(name = "RANK_CODE", nullable = false, length = 50)
    private String rankCode;

    @Column(name = "PHONE", length = 50)
    private String phone;

    @Column(name = "EMAIL", length = 255)
    private String email;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private Employee createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private Employee modifiedBy;

    public Employee() {}

    public Employee(Integer employeeId, String employeeCode, String employeeName, String department, String position,
                    String gender, Integer employeeType, String rankCode, String phone, String email,
                    Boolean isActive, LocalDateTime createdDate, LocalDateTime modifiedDate,
                    Employee createdBy, Employee modifiedBy) {
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
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }
}
