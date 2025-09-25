package com.example.demo.Asset.service;

import com.example.demo.Asset.dao.AssetDAO;
import com.example.demo.Asset.dto.AssetDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private final AssetDAO assetDAO;

    public AssetServiceImpl(AssetDAO assetDAO) {
        this.assetDAO = assetDAO;
    }

    @Override
    public AssetDTO createAsset(AssetDTO assetDTO) {
        return assetDAO.createAsset(assetDTO);
    }

    @Override
    public AssetDTO getAssetById(Integer id) {
        return assetDAO.getAssetById(id);
    }

    @Override
    public List<AssetDTO> getAllAssets() {
        return assetDAO.getAllAssets();
    }

    @Override
    public AssetDTO updateAsset(Integer id, AssetDTO assetDTO) {
        return assetDAO.updateAsset(id, assetDTO);
    }

    @Override
    public void deleteAsset(Integer id) {
        assetDAO.deleteAsset(id);
    }

    @Override
    public List<String> validateAsset(AssetDTO assetDTO) {
        return assetDAO.validateAsset(assetDTO);
    }
}