package com.example.demo.Employee.service;

import com.example.demo.Employee.dao.EmployeeDAO;
import com.example.demo.Employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    // Constructor injection
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    @Override
    public List<String> validateEmployee(Employee employee) {
        return employeeDAO.validateEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        employeeDAO.updateEmployee(id, employee);
        return employee;
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeDAO.deleteEmployee(id);
    }

    private final EmployeeDAO employeeDAO;
    @Override
    public Employee createEmployee(Employee employee) {
        employeeDAO.createEmployee(employee);
        return employee;
    }
}
