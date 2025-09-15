package com.example.demo.AssetFixedDecrease.service;

import com.example.demo.AssetFixedDecrease.dao.AssetFixedDecreaseDAO;
import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFixedDecreaseServiceImpl implements AssetFixedDecreaseService {
    public AssetFixedDecreaseServiceImpl(AssetFixedDecreaseDAO assetFixedDecreaseDAO) {
        this.assetFixedDecreaseDAO = assetFixedDecreaseDAO;
    }

    @Override
    public List<String> validateAssetFixedDecrease(AssetFixedDecrease assetFixedDecrease) {
        return assetFixedDecreaseDAO.validateAssetFixedDecrease(assetFixedDecrease);
    }

    @Override
    public AssetFixedDecrease createAssetFixedDecrease(AssetFixedDecrease assetFixedDecrease) {
        return assetFixedDecreaseDAO.createAssetFixedDecrease(assetFixedDecrease);
    }

    @Override
    public AssetFixedDecrease getAssetFixedDecreaseById(Integer id) {
        return assetFixedDecreaseDAO.getAssetFixedDecreaseById(id);
    }

    @Override
    public List<AssetFixedDecrease> getAllAssetFixedDecreases() {
        return assetFixedDecreaseDAO.getAllAssetFixedDecreases();
    }

    @Override
    public AssetFixedDecrease updateAssetFixedDecrease(Integer id, AssetFixedDecrease assetFixedDecrease) {
        return assetFixedDecreaseDAO.updateAssetFixedDecrease(id, assetFixedDecrease);
    }

    @Override
    public void deleteAssetFixedDecrease(Integer id) {
        assetFixedDecreaseDAO.deleteAssetFixedDecrease(id);
    }

    private final AssetFixedDecreaseDAO assetFixedDecreaseDAO;

}