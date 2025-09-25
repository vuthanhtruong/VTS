package com.example.demo.Asset.service;

import com.example.demo.Asset.dto.AssetDTO;
import java.util.List;

public interface AssetService {
    AssetDTO createAsset(AssetDTO assetDTO);
    AssetDTO getAssetById(Integer id);
    List<AssetDTO> getAllAssets();
    AssetDTO updateAsset(Integer id, AssetDTO assetDTO);
    void deleteAsset(Integer id);
    List<String> validateAsset(AssetDTO assetDTO);
}