package com.example.demo.AssetFixedDecreaseReason.api;

import com.example.demo.AssetFixedDecreaseReason.model.AssetFixedDecreaseReason;
import com.example.demo.AssetFixedDecreaseReason.service.AssetFixedDecreaseReasonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-fixed-decrease-reasons")
@Validated
public class AssetFixedDecreaseReasonController {

    private final AssetFixedDecreaseReasonService assetFixedDecreaseReasonService;

    public AssetFixedDecreaseReasonController(AssetFixedDecreaseReasonService assetFixedDecreaseReasonService) {
        this.assetFixedDecreaseReasonService = assetFixedDecreaseReasonService;
    }

    @PostMapping
    public ResponseEntity<?> createAssetFixedDecreaseReason(@Valid @RequestBody AssetFixedDecreaseReason assetFixedDecreaseReason) {
        List<String> errors = assetFixedDecreaseReasonService.validateAssetFixedDecreaseReason(assetFixedDecreaseReason);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecreaseReason createdAssetFixedDecreaseReason = assetFixedDecreaseReasonService.createAssetFixedDecreaseReason(assetFixedDecreaseReason);
            return new ResponseEntity<>(createdAssetFixedDecreaseReason, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset fixed decrease reason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedDecreaseReasonById(@PathVariable Integer id) {
        try {
            AssetFixedDecreaseReason assetFixedDecreaseReason = assetFixedDecreaseReasonService.getAssetFixedDecreaseReasonById(id);
            return new ResponseEntity<>(assetFixedDecreaseReason, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed decrease reason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixedDecreaseReasons() {
        try {
            List<AssetFixedDecreaseReason> assetFixedDecreaseReasons = assetFixedDecreaseReasonService.getAllAssetFixedDecreaseReasons();
            return new ResponseEntity<>(assetFixedDecreaseReasons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed decrease reasons: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixedDecreaseReason(@PathVariable Integer id, @Valid @RequestBody AssetFixedDecreaseReason assetFixedDecreaseReason) {
        List<String> errors = assetFixedDecreaseReasonService.validateAssetFixedDecreaseReason(assetFixedDecreaseReason);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecreaseReason updatedAssetFixedDecreaseReason = assetFixedDecreaseReasonService.updateAssetFixedDecreaseReason(id, assetFixedDecreaseReason);
            return new ResponseEntity<>(updatedAssetFixedDecreaseReason, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update asset fixed decrease reason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssetFixedDecreaseReason(@PathVariable Integer id) {
        try {
            assetFixedDecreaseReasonService.deleteAssetFixedDecreaseReason(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete asset fixed decrease reason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}