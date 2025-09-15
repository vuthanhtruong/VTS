package com.example.demo.AssetFixedType.service;

import com.example.demo.AssetFixedType.dao.AssetFixedTypeDAO;
import com.example.demo.AssetFixedType.dao.AssetFixedTypeDAOImpl;
import com.example.demo.AssetFixedType.model.AssetFixedType;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssetFixedTypeServiceImpl implements AssetFixedTypeService {
    private final AssetFixedTypeDAO assetFixedTypeDAO;

    public AssetFixedTypeServiceImpl(AssetFixedTypeDAO assetFixedTypeDAO) {
        this.assetFixedTypeDAO = assetFixedTypeDAO;
    }

    @Override
    public List<String> validateAssetFixedType(AssetFixedType assetFixedType) {
        return assetFixedTypeDAO.validateAssetFixedType(assetFixedType);
    }

    @Override
    public AssetFixedType createAssetFixedType(AssetFixedType assetFixedType) {
        return assetFixedTypeDAO.createAssetFixedType(assetFixedType);
    }

    @Override
    public AssetFixedType getAssetFixedTypeById(Integer id) {
        return assetFixedTypeDAO.getAssetFixedTypeById(id);
    }

    @Override
    public List<AssetFixedType> getAllAssetFixedTypes() {
        return assetFixedTypeDAO.getAllAssetFixedTypes();
    }

    @Override
    public AssetFixedType updateAssetFixedType(Integer id, AssetFixedType assetFixedType) {
        return assetFixedTypeDAO.updateAssetFixedType(id, assetFixedType);
    }

    @Override
    public void deleteAssetFixedType(Integer id) {
        assetFixedTypeDAO.deleteAssetFixedType(id);
    }
}
