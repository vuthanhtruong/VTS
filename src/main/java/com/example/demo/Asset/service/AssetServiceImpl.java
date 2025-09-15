package com.example.demo.Asset.service;

import com.example.demo.Asset.dao.AssetDAO;
import com.example.demo.Asset.model.Asset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private AssetDAO assetDAO;
    @Override
    public Asset createAsset(Asset asset) {
        return assetDAO.createAsset(asset);
    }

    @Override
    public Asset getAssetById(Integer id) {
        return assetDAO.getAssetById(id);
    }

    @Override
    public List<Asset> getAllAssets() {
        return assetDAO.getAllAssets();
    }

    @Override
    public Asset updateAsset(Integer id, Asset asset) {
        return assetDAO.updateAsset(id, asset);
    }

    @Override
    public void deleteAsset(Integer id) {
        assetDAO.deleteAsset(id);
    }

    @Override
    public List<String> validateAsset(Asset asset) {
        return assetDAO.validateAsset(asset);
    }
}
