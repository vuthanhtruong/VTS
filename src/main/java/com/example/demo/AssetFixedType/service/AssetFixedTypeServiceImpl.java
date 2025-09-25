package com.example.demo.AssetFixedType.service;

import com.example.demo.AssetFixedType.dao.AssetFixedTypeDAO;
import com.example.demo.AssetFixedType.dto.AssetFixedTypeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFixedTypeServiceImpl implements AssetFixedTypeService {
    private final AssetFixedTypeDAO assetFixedTypeDAO;

    public AssetFixedTypeServiceImpl(AssetFixedTypeDAO assetFixedTypeDAO) {
        this.assetFixedTypeDAO = assetFixedTypeDAO;
    }

    @Override
    public List<String> validateAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO) {
        return assetFixedTypeDAO.validateAssetFixedType(assetFixedTypeDTO);
    }

    @Override
    public AssetFixedTypeDTO createAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO) {
        return assetFixedTypeDAO.createAssetFixedType(assetFixedTypeDTO);
    }

    @Override
    public AssetFixedTypeDTO getAssetFixedTypeById(Integer id) {
        return assetFixedTypeDAO.getAssetFixedTypeById(id);
    }

    @Override
    public List<AssetFixedTypeDTO> getAllAssetFixedTypes() {
        return assetFixedTypeDAO.getAllAssetFixedTypes();
    }

    @Override
    public AssetFixedTypeDTO updateAssetFixedType(Integer id, AssetFixedTypeDTO assetFixedTypeDTO) {
        return assetFixedTypeDAO.updateAssetFixedType(id, assetFixedTypeDTO);
    }

    @Override
    public void deleteAssetFixedType(Integer id) {
        assetFixedTypeDAO.deleteAssetFixedType(id);
    }
}