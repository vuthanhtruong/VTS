package com.example.demo.Asset.api;

import com.example.demo.Asset.dto.AssetDTO;
import com.example.demo.Asset.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<?> createAsset(@Valid @RequestBody AssetDTO assetDTO) {
        List<String> errors = assetService.validateAsset(assetDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetDTO createdAsset = assetService.createAsset(assetDTO);
            return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetById(@PathVariable Integer id) {
        try {
            AssetDTO asset = assetService.getAssetById(id);
            return new ResponseEntity<>(asset, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssets() {
        try {
            List<AssetDTO> assets = assetService.getAllAssets();
            return new ResponseEntity<>(assets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve assets: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Integer id, @Valid @RequestBody AssetDTO assetDTO) {
        List<String> errors = assetService.validateAsset(assetDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetDTO updatedAsset = assetService.updateAsset(id, assetDTO);
            return new ResponseEntity<>(updatedAsset, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update asset: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Integer id) {
        try {
            assetService.deleteAsset(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete asset: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}