package com.example.demo.AssetFixedType.dao;

import com.example.demo.AssetFixedType.dto.AssetFixedTypeDTO;
import java.util.List;

public interface AssetFixedTypeDAO {
    List<String> validateAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO);
    AssetFixedTypeDTO createAssetFixedType(AssetFixedTypeDTO assetFixedTypeDTO);
    AssetFixedTypeDTO getAssetFixedTypeById(Integer id);
    List<AssetFixedTypeDTO> getAllAssetFixedTypes();
    AssetFixedTypeDTO updateAssetFixedType(Integer id, AssetFixedTypeDTO assetFixedTypeDTO);
    void deleteAssetFixedType(Integer id);
}