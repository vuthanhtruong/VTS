package com.example.demo.Asset.dao;

import com.example.demo.Asset.dto.AssetDTO;
import java.util.List;

public interface AssetDAO {
    AssetDTO createAsset(AssetDTO assetDTO);
    AssetDTO getAssetById(Integer id);
    List<AssetDTO> getAllAssets();
    AssetDTO updateAsset(Integer id, AssetDTO assetDTO);
    void deleteAsset(Integer id);
    List<String> validateAsset(AssetDTO assetDTO);
}