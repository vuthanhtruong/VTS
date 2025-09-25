package com.example.demo.Employee.service;

import com.example.demo.Employee.dao.EmployeeDAO;
import com.example.demo.Employee.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDAO;

    // Constructor injection
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<String> validateEmployee(EmployeeDTO employeeDTO) {
        return employeeDAO.validateEmployee(employeeDTO);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        return employeeDAO.createEmployee(employeeDTO);
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        return employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        return employeeDAO.updateEmployee(id, employeeDTO);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeDAO.deleteEmployee(id);
    }
}