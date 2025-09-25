package com.example.demo.AssetFixedDecrease.service;

import com.example.demo.AssetFixedDecrease.dto.AssetFixedDecreaseDTO;
import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;

import java.util.List;

public interface AssetFixedDecreaseService {
    List<String> validateAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO);
    AssetFixedDecreaseDTO createAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO);
    AssetFixedDecreaseDTO getAssetFixedDecreaseById(Integer id);
    List<AssetFixedDecreaseDTO> getAllAssetFixedDecreases();
    AssetFixedDecreaseDTO updateAssetFixedDecrease(Integer id, AssetFixedDecreaseDTO assetFixedDecreaseDTO);
    void deleteAssetFixedDecrease(Integer id);
    List<AssetFixedDecreaseDTO> getPaginatedAssetFixedDecreases(int page, int size);
    long countAssetFixedDecreases();
}
