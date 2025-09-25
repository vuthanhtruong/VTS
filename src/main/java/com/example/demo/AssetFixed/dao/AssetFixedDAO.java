package com.example.demo.AssetFixed.dao;

import com.example.demo.AssetFixed.dto.AssetFixedDTO;
import java.util.List;

public interface AssetFixedDAO {
    AssetFixedDTO createAssetFixed(AssetFixedDTO assetFixedDTO);
    AssetFixedDTO getAssetFixedById(Integer id);
    List<AssetFixedDTO> getAllAssetFixed();
    AssetFixedDTO updateAssetFixed(Integer id, AssetFixedDTO assetFixedDTO);
    void deleteAssetFixed(Integer id);
    List<String> validateAssetFixed(AssetFixedDTO assetFixedDTO);
}