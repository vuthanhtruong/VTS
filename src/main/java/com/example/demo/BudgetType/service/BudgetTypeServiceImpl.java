package com.example.demo.BudgetType.service;

import com.example.demo.BudgetType.dao.BudgetTypeDAO;
import com.example.demo.BudgetType.dto.BudgetTypeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetTypeServiceImpl implements BudgetTypeService {
    private final BudgetTypeDAO budgetTypeDAO;

    public BudgetTypeServiceImpl(BudgetTypeDAO budgetTypeDAO) {
        this.budgetTypeDAO = budgetTypeDAO;
    }

    @Override
    public List<String> validateBudgetType(BudgetTypeDTO budgetTypeDTO) {
        return budgetTypeDAO.validateBudgetType(budgetTypeDTO);
    }

    @Override
    public BudgetTypeDTO createBudgetType(BudgetTypeDTO budgetTypeDTO) {
        return budgetTypeDAO.createBudgetType(budgetTypeDTO);
    }

    @Override
    public BudgetTypeDTO getBudgetTypeById(Integer id) {
        return budgetTypeDAO.getBudgetTypeById(id);
    }

    @Override
    public List<BudgetTypeDTO> getAllBudgetTypes() {
        return budgetTypeDAO.getAllBudgetTypes();
    }

    @Override
    public BudgetTypeDTO updateBudgetType(Integer id, BudgetTypeDTO budgetTypeDTO) {
        return budgetTypeDAO.updateBudgetType(id, budgetTypeDTO);
    }

    @Override
    public void deleteBudgetType(Integer id) {
        budgetTypeDAO.deleteBudgetType(id);
    }
}