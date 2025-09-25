package com.example.demo.AssetFixedDecreaseReason.service;

import com.example.demo.AssetFixedDecreaseReason.dao.AssetFixedDecreaseReasonDAO;
import com.example.demo.AssetFixedDecreaseReason.dto.AssetFixedDecreaseReasonDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFixedDecreaseReasonServiceImpl implements AssetFixedDecreaseReasonService {
    private final AssetFixedDecreaseReasonDAO assetFixedDecreaseReasonDAO;

    public AssetFixedDecreaseReasonServiceImpl(AssetFixedDecreaseReasonDAO assetFixedDecreaseReasonDAO) {
        this.assetFixedDecreaseReasonDAO = assetFixedDecreaseReasonDAO;
    }

    @Override
    public List<String> validateAssetFixedDecreaseReason(AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO) {
        return assetFixedDecreaseReasonDAO.validateAssetFixedDecreaseReason(assetFixedDecreaseReasonDTO);
    }

    @Override
    public AssetFixedDecreaseReasonDTO createAssetFixedDecreaseReason(AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO) {
        return assetFixedDecreaseReasonDAO.createAssetFixedDecreaseReason(assetFixedDecreaseReasonDTO);
    }

    @Override
    public AssetFixedDecreaseReasonDTO getAssetFixedDecreaseReasonById(Integer id) {
        return assetFixedDecreaseReasonDAO.getAssetFixedDecreaseReasonById(id);
    }

    @Override
    public List<AssetFixedDecreaseReasonDTO> getAllAssetFixedDecreaseReasons() {
        return assetFixedDecreaseReasonDAO.getAllAssetFixedDecreaseReasons();
    }

    @Override
    public AssetFixedDecreaseReasonDTO updateAssetFixedDecreaseReason(Integer id, AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO) {
        return assetFixedDecreaseReasonDAO.updateAssetFixedDecreaseReason(id, assetFixedDecreaseReasonDTO);
    }

    @Override
    public void deleteAssetFixedDecreaseReason(Integer id) {
        assetFixedDecreaseReasonDAO.deleteAssetFixedDecreaseReason(id);
    }
}