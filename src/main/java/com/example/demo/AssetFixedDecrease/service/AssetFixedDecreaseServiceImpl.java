package com.example.demo.AssetFixedDecrease.service;

import com.example.demo.AssetFixedDecrease.dao.AssetFixedDecreaseDAO;
import com.example.demo.AssetFixedDecrease.dto.AssetFixedDecreaseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFixedDecreaseServiceImpl implements AssetFixedDecreaseService {
    @Override
    public List<AssetFixedDecreaseDTO> getPaginatedAssetFixedDecreases(int page, int size) {
        return assetFixedDecreaseDAO.getPaginatedAssetFixedDecreases(page, size);
    }

    @Override
    public long countAssetFixedDecreases() {
        return assetFixedDecreaseDAO.countAssetFixedDecreases();
    }

    private final AssetFixedDecreaseDAO assetFixedDecreaseDAO;

    public AssetFixedDecreaseServiceImpl(AssetFixedDecreaseDAO assetFixedDecreaseDAO) {
        this.assetFixedDecreaseDAO = assetFixedDecreaseDAO;
    }

    @Override
    public List<String> validateAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        return assetFixedDecreaseDAO.validateAssetFixedDecrease(assetFixedDecreaseDTO);
    }

    @Override
    public AssetFixedDecreaseDTO createAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        return assetFixedDecreaseDAO.createAssetFixedDecrease(assetFixedDecreaseDTO);
    }

    @Override
    public AssetFixedDecreaseDTO getAssetFixedDecreaseById(Integer id) {
        return assetFixedDecreaseDAO.getAssetFixedDecreaseById(id);
    }

    @Override
    public List<AssetFixedDecreaseDTO> getAllAssetFixedDecreases() {
        return assetFixedDecreaseDAO.getAllAssetFixedDecreases();
    }

    @Override
    public AssetFixedDecreaseDTO updateAssetFixedDecrease(Integer id, AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        return assetFixedDecreaseDAO.updateAssetFixedDecrease(id, assetFixedDecreaseDTO);
    }

    @Override
    public void deleteAssetFixedDecrease(Integer id) {
        assetFixedDecreaseDAO.deleteAssetFixedDecrease(id);
    }
}