package com.example.demo.AssetFixedDecrease.dao;

import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;

import java.util.List;

public interface AssetFixedDecreaseDAO {
    List<String> validateAssetFixedDecrease(AssetFixedDecrease assetFixedDecrease);
    AssetFixedDecrease createAssetFixedDecrease(AssetFixedDecrease assetFixedDecrease);
    AssetFixedDecrease getAssetFixedDecreaseById(Integer id);
    List<AssetFixedDecrease> getAllAssetFixedDecreases();
    AssetFixedDecrease updateAssetFixedDecrease(Integer id, AssetFixedDecrease assetFixedDecrease);
    void deleteAssetFixedDecrease(Integer id);
}
