package com.example.demo.AssetFixedType.api;

import com.example.demo.AssetFixedType.model.AssetFixedType;
import com.example.demo.AssetFixedType.service.AssetFixedTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-fixed-types")
@Validated
public class AssetFixedTypeController {

    private final AssetFixedTypeService assetFixedTypeService;

    public AssetFixedTypeController(AssetFixedTypeService assetFixedTypeService) {
        this.assetFixedTypeService = assetFixedTypeService;
    }

    @PostMapping
    public ResponseEntity<?> createAssetFixedType(@Valid @RequestBody AssetFixedType assetFixedType) {
        List<String> errors = assetFixedTypeService.validateAssetFixedType(assetFixedType);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedType createdAssetFixedType = assetFixedTypeService.createAssetFixedType(assetFixedType);
            return new ResponseEntity<>(createdAssetFixedType, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset fixed type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedTypeById(@PathVariable Integer id) {
        try {
            AssetFixedType assetFixedType = assetFixedTypeService.getAssetFixedTypeById(id);
            return new ResponseEntity<>(assetFixedType, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixedTypes() {
        try {
            List<AssetFixedType> assetFixedTypes = assetFixedTypeService.getAllAssetFixedTypes();
            return new ResponseEntity<>(assetFixedTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed types: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixedType(@PathVariable Integer id, @Valid @RequestBody AssetFixedType assetFixedType) {
        List<String> errors = assetFixedTypeService.validateAssetFixedType(assetFixedType);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedType updatedAssetFixedType = assetFixedTypeService.updateAssetFixedType(id, assetFixedType);
            return new ResponseEntity<>(updatedAssetFixedType, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update asset fixed type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssetFixedType(@PathVariable Integer id) {
        try {
            assetFixedTypeService.deleteAssetFixedType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete asset fixed type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}