package com.example.demo.AssetFixedType.api;

import com.example.demo.AssetFixedType.dto.AssetFixedTypeDTO;
import com.example.demo.AssetFixedType.service.AssetFixedTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-fixed-types")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AssetFixedTypeController {

    private final AssetFixedTypeService assetFixedTypeService;

    public AssetFixedTypeController(AssetFixedTypeService assetFixedTypeService) {
        this.assetFixedTypeService = assetFixedTypeService;
    }

    @PostMapping
    public ResponseEntity<?> createAssetFixedType(@Valid @RequestBody AssetFixedTypeDTO assetFixedTypeDTO) {
        List<String> errors = assetFixedTypeService.validateAssetFixedType(assetFixedTypeDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedTypeDTO createdAssetFixedType = assetFixedTypeService.createAssetFixedType(assetFixedTypeDTO);
            return new ResponseEntity<>(createdAssetFixedType, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset fixed type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedTypeById(@PathVariable Integer id) {
        try {
            AssetFixedTypeDTO assetFixedType = assetFixedTypeService.getAssetFixedTypeById(id);
            return new ResponseEntity<>(assetFixedType, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed type: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixedTypes() {
        try {
            List<AssetFixedTypeDTO> assetFixedTypes = assetFixedTypeService.getAllAssetFixedTypes();
            return new ResponseEntity<>(assetFixedTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed types: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixedType(@PathVariable Integer id, @Valid @RequestBody AssetFixedTypeDTO assetFixedTypeDTO) {
        List<String> errors = assetFixedTypeService.validateAssetFixedType(assetFixedTypeDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedTypeDTO updatedAssetFixedType = assetFixedTypeService.updateAssetFixedType(id, assetFixedTypeDTO);
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