package com.example.demo.Employee.dao;

import com.example.demo.Employee.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeDAO {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Integer id);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    void deleteEmployee(Integer id);
    List<String> validateEmployee(EmployeeDTO employeeDTO);
}