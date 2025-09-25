package com.example.demo.AssetFixed.service;

import com.example.demo.AssetFixed.dto.AssetFixedDTO;
import com.example.demo.AssetFixed.model.AssetFixed;

import java.util.List;

public interface AssetFixedSerivce {
    AssetFixedDTO createAssetFixed(AssetFixedDTO assetFixedDTO);
    AssetFixedDTO getAssetFixedById(Integer id);
    List<AssetFixedDTO> getAllAssetFixed();
    AssetFixedDTO updateAssetFixed(Integer id, AssetFixedDTO assetFixedDTO);
    void deleteAssetFixed(Integer id);
    List<String> validateAssetFixed(AssetFixedDTO assetFixedDTO);
}
