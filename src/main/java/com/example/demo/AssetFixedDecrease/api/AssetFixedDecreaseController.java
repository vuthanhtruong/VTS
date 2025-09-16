package com.example.demo.AssetFixedDecrease.api;

import com.example.demo.AssetFixedDecrease.model.AssetFixedDecrease;
import com.example.demo.AssetFixedDecrease.service.AssetFixedDecreaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/asset-fixed-decreases")
@Validated
public class AssetFixedDecreaseController {

    private final AssetFixedDecreaseService assetFixedDecreaseService;

    public AssetFixedDecreaseController(AssetFixedDecreaseService assetFixedDecreaseService) {
        this.assetFixedDecreaseService = assetFixedDecreaseService;
    }

    @PostMapping
    public ResponseEntity<?> createAssetFixedDecrease(@Valid @RequestBody AssetFixedDecrease assetFixedDecrease) {
        List<String> errors = assetFixedDecreaseService.validateAssetFixedDecrease(assetFixedDecrease);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecrease createdAssetFixedDecrease = assetFixedDecreaseService.createAssetFixedDecrease(assetFixedDecrease);
            return new ResponseEntity<>(createdAssetFixedDecrease, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset fixed decrease: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedDecreaseById(@PathVariable Integer id) {
        try {
            AssetFixedDecrease assetFixedDecrease = assetFixedDecreaseService.getAssetFixedDecreaseById(id);
            return new ResponseEntity<>(assetFixedDecrease, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed decrease: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixedDecreases() {
        try {
            List<AssetFixedDecrease> assetFixedDecreases = assetFixedDecreaseService.getAllAssetFixedDecreases();
            return new ResponseEntity<>(assetFixedDecreases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed decreases: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixedDecrease(@PathVariable Integer id, @Valid @RequestBody AssetFixedDecrease assetFixedDecrease) {
        List<String> errors = assetFixedDecreaseService.validateAssetFixedDecrease(assetFixedDecrease);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecrease updatedAssetFixedDecrease = assetFixedDecreaseService.updateAssetFixedDecrease(id, assetFixedDecrease);
            return new ResponseEntity<>(updatedAssetFixedDecrease, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update asset fixed decrease: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssetFixedDecrease(@PathVariable Integer id) {
        try {
            assetFixedDecreaseService.deleteAssetFixedDecrease(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete asset fixed decrease: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}