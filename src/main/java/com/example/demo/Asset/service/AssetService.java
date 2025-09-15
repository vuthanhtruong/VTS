package com.example.demo.Asset.service;

import com.example.demo.Asset.model.Asset;

import java.util.List;

public interface AssetService {
    Asset createAsset(Asset asset);
    Asset getAssetById(Integer id);
    List<Asset> getAllAssets();
    Asset updateAsset(Integer id, Asset asset);
    void deleteAsset(Integer id);
    List<String> validateAsset(Asset asset);
}
