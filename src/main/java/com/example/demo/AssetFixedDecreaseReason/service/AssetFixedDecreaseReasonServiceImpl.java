package com.example.demo.AssetFixedDecreaseReason.service;

import com.example.demo.AssetFixedDecreaseReason.dao.AssetFixedDecreaseReasonDAO;
import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFixedDecreaseReasonServiceImpl implements AssetFixedDecreaseReasonService {
    @Override
    public List<String> validateAssetFixedDecreaseReason(AssetFixedDecreaseReason assetFixedDecreaseReason) {
        return assetFixedDecreaseReasonDAO.validateAssetFixedDecreaseReason(assetFixedDecreaseReason);
    }

    @Override
    public AssetFixedDecreaseReason createAssetFixedDecreaseReason(AssetFixedDecreaseReason assetFixedDecreaseReason) {
        return assetFixedDecreaseReasonDAO.createAssetFixedDecreaseReason(assetFixedDecreaseReason);
    }

    @Override
    public AssetFixedDecreaseReason getAssetFixedDecreaseReasonById(Integer id) {
        return assetFixedDecreaseReasonDAO.getAssetFixedDecreaseReasonById(id);
    }

    @Override
    public List<AssetFixedDecreaseReason> getAllAssetFixedDecreaseReasons() {
        return assetFixedDecreaseReasonDAO.getAllAssetFixedDecreaseReasons();
    }

    @Override
    public AssetFixedDecreaseReason updateAssetFixedDecreaseReason(Integer id, AssetFixedDecreaseReason assetFixedDecreaseReason) {
        return assetFixedDecreaseReasonDAO.updateAssetFixedDecreaseReason(id, assetFixedDecreaseReason);
    }

    @Override
    public void deleteAssetFixedDecreaseReason(Integer id) {

    }

    private final AssetFixedDecreaseReasonDAO assetFixedDecreaseReasonDAO;

    @Autowired
    public AssetFixedDecreaseReasonServiceImpl(AssetFixedDecreaseReasonDAO assetFixedDecreaseReasonDAO) {
        this.assetFixedDecreaseReasonDAO = assetFixedDecreaseReasonDAO;
    }


}