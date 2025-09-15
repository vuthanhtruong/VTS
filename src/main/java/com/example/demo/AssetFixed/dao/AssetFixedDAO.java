package com.example.demo.AssetFixed.dao;

import com.example.demo.AssetFixed.model.AssetFixed;

import java.util.List;

public interface AssetFixedDAO {
    AssetFixed createAssetFixed(AssetFixed assetFixed);
    AssetFixed getAssetFixedById(Integer id);
    List<AssetFixed> getAllAssetFixed();
    AssetFixed updateAssetFixed(Integer id, AssetFixed assetFixed);
    void deleteAssetFixed(Integer id);
    List<String> validateAssetFixed(AssetFixed assetFixed);
}
