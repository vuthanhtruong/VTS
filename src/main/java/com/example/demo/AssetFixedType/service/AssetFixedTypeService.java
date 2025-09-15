package com.example.demo.AssetFixedType.service;

import com.example.demo.AssetFixedType.model.AssetFixedType;

import java.util.List;

public interface AssetFixedTypeService {
    List<String> validateAssetFixedType(AssetFixedType assetFixedType);
    AssetFixedType createAssetFixedType(AssetFixedType assetFixedType);
    AssetFixedType getAssetFixedTypeById(Integer id);
    List<AssetFixedType> getAllAssetFixedTypes();
    AssetFixedType updateAssetFixedType(Integer id, AssetFixedType assetFixedType);
    void deleteAssetFixedType(Integer id);
}
