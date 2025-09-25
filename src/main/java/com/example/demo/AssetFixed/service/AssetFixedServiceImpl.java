package com.example.demo.AssetFixed.service;

import com.example.demo.AssetFixed.dao.AssetFixedDAO;
import com.example.demo.AssetFixed.dto.AssetFixedDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFixedServiceImpl implements AssetFixedSerivce {
    private final AssetFixedDAO assetFixedDAO;

    public AssetFixedServiceImpl(AssetFixedDAO assetFixedDAO) {
        this.assetFixedDAO = assetFixedDAO;
    }

    @Override
    public AssetFixedDTO createAssetFixed(AssetFixedDTO assetFixedDTO) {
        return assetFixedDAO.createAssetFixed(assetFixedDTO);
    }

    @Override
    public AssetFixedDTO getAssetFixedById(Integer id) {
        return assetFixedDAO.getAssetFixedById(id);
    }

    @Override
    public List<AssetFixedDTO> getAllAssetFixed() {
        return assetFixedDAO.getAllAssetFixed();
    }

    @Override
    public AssetFixedDTO updateAssetFixed(Integer id, AssetFixedDTO assetFixedDTO) {
        return assetFixedDAO.updateAssetFixed(id, assetFixedDTO);
    }

    @Override
    public void deleteAssetFixed(Integer id) {
        assetFixedDAO.deleteAssetFixed(id);
    }

    @Override
    public List<String> validateAssetFixed(AssetFixedDTO assetFixedDTO) {
        return assetFixedDAO.validateAssetFixed(assetFixedDTO);
    }
}