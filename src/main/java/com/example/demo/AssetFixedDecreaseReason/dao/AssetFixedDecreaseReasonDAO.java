package com.example.demo.AssetFixedDecreaseReason.dao;

import com.example.demo.AssetFixedDecreaseReason.dto.AssetFixedDecreaseReasonDTO;
import java.util.List;

public interface AssetFixedDecreaseReasonDAO {
    List<String> validateAssetFixedDecreaseReason(AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO);
    AssetFixedDecreaseReasonDTO createAssetFixedDecreaseReason(AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO);
    AssetFixedDecreaseReasonDTO getAssetFixedDecreaseReasonById(Integer id);
    List<AssetFixedDecreaseReasonDTO> getAllAssetFixedDecreaseReasons();
    AssetFixedDecreaseReasonDTO updateAssetFixedDecreaseReason(Integer id, AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO);
    void deleteAssetFixedDecreaseReason(Integer id);
}