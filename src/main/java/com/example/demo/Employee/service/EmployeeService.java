package com.example.demo.Employee.service;

import com.example.demo.Employee.dto.EmployeeDTO;
import com.example.demo.Employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Integer id);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    void deleteEmployee(Integer id);
    List<String> validateEmployee(EmployeeDTO employeeDTO);
}
