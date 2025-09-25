package com.example.demo.AssetFixedType.service;

import com.example.demo.AssetFixedType.dto.AssetFixedTypeDTO;
import java.util.List;

public interface AssetFixedTypeService {
    List<String> validateAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO);
    AssetFixedTypeDTO createAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO);
    AssetFixedTypeDTO getAssetFixedTypeById(Integer id);
    List<AssetFixedTypeDTO> getAllAssetFixedTypes();
    AssetFixedTypeDTO updateAssetFixedType(Integer id, AssetFixedTypeDTO assetFixedTypeDTO);
    void deleteAssetFixedType(Integer id);
}