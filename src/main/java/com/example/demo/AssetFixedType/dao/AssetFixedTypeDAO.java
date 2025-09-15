package com.example.demo.AssetFixedType.dao;

import com.example.demo.AssetFixedType.model.AssetFixedType;

import java.util.List;

public interface AssetFixedTypeDAO {
    List<String> validateAssetFixedType(AssetFixedType assetFixedType);
    AssetFixedType createAssetFixedType(AssetFixedType assetFixedType);
    AssetFixedType getAssetFixedTypeById(Integer id);
    List<AssetFixedType> getAllAssetFixedTypes();
    AssetFixedType updateAssetFixedType(Integer id, AssetFixedType assetFixedType);
    void deleteAssetFixedType(Integer id);
}
