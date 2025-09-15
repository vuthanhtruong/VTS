package com.example.demo.AssetFixedDecreaseReason.service;

import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;

import java.util.List;

public interface AssetFixedDecreaseReasonService {
    List<String> validateAssetFixedDecreaseReason(AssetFixedDecreaseReason assetFixedDecreaseReason);
    AssetFixedDecreaseReason createAssetFixedDecreaseReason(AssetFixedDecreaseReason assetFixedDecreaseReason);
    AssetFixedDecreaseReason getAssetFixedDecreaseReasonById(Integer id);
    List<AssetFixedDecreaseReason> getAllAssetFixedDecreaseReasons();
    AssetFixedDecreaseReason updateAssetFixedDecreaseReason(Integer id, AssetFixedDecreaseReason assetFixedDecreaseReason);
    void deleteAssetFixedDecreaseReason(Integer id);
}
