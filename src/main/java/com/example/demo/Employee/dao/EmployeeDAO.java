package com.example.demo.Employee.dao;

import com.example.demo.Employee.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Integer id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Integer id, Employee employee);
    void deleteEmployee(Integer id);
    List<String> validateEmployee(Employee employee);
}
