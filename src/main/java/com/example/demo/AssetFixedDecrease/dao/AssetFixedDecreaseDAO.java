package com.example.demo.AssetFixedDecrease.dao;

import com.example.demo.AssetFixedDecrease.dto.AssetFixedDecreaseDTO;
import java.util.List;

public interface AssetFixedDecreaseDAO {
    List<String> validateAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO);
    AssetFixedDecreaseDTO createAssetFixedDecrease(AssetFixedDecreaseDTO assetFixedDecreaseDTO);
    AssetFixedDecreaseDTO getAssetFixedDecreaseById(Integer id);
    List<AssetFixedDecreaseDTO> getAllAssetFixedDecreases();
    AssetFixedDecreaseDTO updateAssetFixedDecrease(Integer id, AssetFixedDecreaseDTO assetFixedDecreaseDTO);
    void deleteAssetFixedDecrease(Integer id);
    List<AssetFixedDecreaseDTO> getPaginatedAssetFixedDecreases(int page, int size);
    long countAssetFixedDecreases();
}