package com.example.demo.AssetFixedDecreaseReason.api;

import com.example.demo.AssetFixedDecreaseReason.dto.AssetFixedDecreaseReasonDTO;
import com.example.demo.AssetFixedDecreaseReason.service.AssetFixedDecreaseReasonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-fixed-decrease-reasons")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AssetFixedDecreaseReasonController {

    private final AssetFixedDecreaseReasonService assetFixedDecreaseReasonService;

    public AssetFixedDecreaseReasonController(AssetFixedDecreaseReasonService assetFixedDecreaseReasonService) {
        this.assetFixedDecreaseReasonService = assetFixedDecreaseReasonService;
    }

    @PostMapping
    public ResponseEntity<?> createAssetFixedDecreaseReason(@Valid @RequestBody AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO) {
        List<String> errors = assetFixedDecreaseReasonService.validateAssetFixedDecreaseReason(assetFixedDecreaseReasonDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecreaseReasonDTO createdAssetFixedDecreaseReason = assetFixedDecreaseReasonService.createAssetFixedDecreaseReason(assetFixedDecreaseReasonDTO);
            return new ResponseEntity<>(createdAssetFixedDecreaseReason, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset fixed decrease reason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedDecreaseReasonById(@PathVariable Integer id) {
        try {
            AssetFixedDecreaseReasonDTO assetFixedDecreaseReason = assetFixedDecreaseReasonService.getAssetFixedDecreaseReasonById(id);
            return new ResponseEntity<>(assetFixedDecreaseReason, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed decrease reason: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixedDecreaseReasons() {
        try {
            List<AssetFixedDecreaseReasonDTO> assetFixedDecreaseReasons = assetFixedDecreaseReasonService.getAllAssetFixedDecreaseReasons();
            return new ResponseEntity<>(assetFixedDecreaseReasons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed decrease reasons: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixedDecreaseReason(@PathVariable Integer id, @Valid @RequestBody AssetFixedDecreaseReasonDTO assetFixedDecreaseReasonDTO) {
        List<String> errors = assetFixedDecreaseReasonService.validateAssetFixedDecreaseReason(assetFixedDecreaseReasonDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecreaseReasonDTO updatedAssetFixedDecreaseReason = assetFixedDecreaseReasonService.updateAssetFixedDecreaseReason(id, assetFixedDecreaseReasonDTO);
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