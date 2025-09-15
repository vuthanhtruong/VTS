package com.example.demo.AssetFixed.service;

import com.example.demo.AssetFixed.dao.AssetFixedDAO;
import com.example.demo.AssetFixed.model.AssetFixed;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssetFixedSerivceImpl implements AssetFixedSerivce {
    private final AssetFixedDAO assetFixedDAO;

    public AssetFixedSerivceImpl(AssetFixedDAO assetFixedDAO) {
        this.assetFixedDAO = assetFixedDAO;
    }

    @Override
    public AssetFixed createAssetFixed(AssetFixed assetFixed) {
        return assetFixedDAO.createAssetFixed(assetFixed);
    }

    @Override
    public AssetFixed getAssetFixedById(Integer id) {
        return assetFixedDAO.getAssetFixedById(id);
    }

    @Override
    public List<AssetFixed> getAllAssetFixed() {
        return assetFixedDAO.getAllAssetFixed();
    }

    @Override
    public AssetFixed updateAssetFixed(Integer id, AssetFixed assetFixed) {
        return assetFixedDAO.updateAssetFixed(id, assetFixed);
    }

    @Override
    public void deleteAssetFixed(Integer id) {
        assetFixedDAO.deleteAssetFixed(id);
    }

    @Override
    public List<String> validateAssetFixed(AssetFixed assetFixed) {
        return assetFixedDAO.validateAssetFixed(assetFixed);
    }
}
