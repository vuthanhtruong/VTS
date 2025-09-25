package com.example.demo.BudgetType.service;

import com.example.demo.BudgetType.dto.BudgetTypeDTO;
import java.util.List;

public interface BudgetTypeService {
    List<String> validateBudgetType(BudgetTypeDTO budgetTypeDTO);
    BudgetTypeDTO createBudgetType(BudgetTypeDTO budgetTypeDTO);
    BudgetTypeDTO getBudgetTypeById(Integer id);
    List<BudgetTypeDTO> getAllBudgetTypes();
    BudgetTypeDTO updateBudgetType(Integer id, BudgetTypeDTO budgetTypeDTO);
    void deleteBudgetType(Integer id);
}