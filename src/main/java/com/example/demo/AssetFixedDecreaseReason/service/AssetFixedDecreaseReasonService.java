package com.example.demo.AssetFixedDecreaseReason.service;

import com.example.demo.AssetFixedDecreaseReason.dto.AssetFixedDecreaseReasonDTO;
import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;

import java.util.List;

public interface AssetFixedDecreaseReasonService {
    List<String> validateAssetFixedDecreaseReason(AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO);
    AssetFixedDecreaseReasonDTO createAssetFixedDecreaseReason(AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO);
    AssetFixedDecreaseReasonDTO getAssetFixedDecreaseReasonById(Integer id);
    List<AssetFixedDecreaseReasonDTO> getAllAssetFixedDecreaseReasons();
    AssetFixedDecreaseReasonDTO updateAssetFixedDecreaseReason(Integer id, AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO);
    void deleteAssetFixedDecreaseReason(Integer id);
}
