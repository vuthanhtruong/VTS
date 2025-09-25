package com.example.demo.AssetFixedDecrease.api;

import com.example.demo.AssetFixedDecrease.dto.AssetFixedDecreaseDTO;
import com.example.demo.AssetFixedDecrease.service.AssetFixedDecreaseService;
import jakarta.validation.Valid;
import lombok.extern.flogger.Flogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createAssetFixedDecrease(@Valid @RequestBody AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        List<String> errors = assetFixedDecreaseService.validateAssetFixedDecrease(assetFixedDecreaseDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecreaseDTO createdAssetFixedDecrease = assetFixedDecreaseService.createAssetFixedDecrease(assetFixedDecreaseDTO);
            return new ResponseEntity<>(createdAssetFixedDecrease, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to create asset fixed decrease: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedDecreaseById(@PathVariable Integer id) {
        try {
            AssetFixedDecreaseDTO assetFixedDecrease = assetFixedDecreaseService.getAssetFixedDecreaseById(id);
            return new ResponseEntity<>(assetFixedDecrease, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve asset fixed decrease: " + e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixedDecreases() {
        try {
            List<AssetFixedDecreaseDTO> assetFixedDecreases = assetFixedDecreaseService.getAllAssetFixedDecreases();
            return new ResponseEntity<>(assetFixedDecreases, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve asset fixed decreases: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getPaginatedAssetFixedDecreases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size <= 0 || size > 100) {
            return new ResponseEntity<>(Map.of("error", "Page must be non-negative and size must be between 1 and 100."), HttpStatus.BAD_REQUEST);
        }
        try {
            List<AssetFixedDecreaseDTO> assetFixedDecreases = assetFixedDecreaseService.getPaginatedAssetFixedDecreases(page, size);
            long totalItems = assetFixedDecreaseService.countAssetFixedDecreases();
            Map<String, Object> response = new HashMap<>();
            response.put("content", assetFixedDecreases);
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("totalItems", totalItems);
            response.put("totalPages", (int) Math.ceil((double) totalItems / size));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to retrieve paginated asset fixed decreases: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixedDecrease(@PathVariable Integer id, @Valid @RequestBody AssetFixedDecreaseDTO assetFixedDecreaseDTO) {
        List<String> errors = assetFixedDecreaseService.validateAssetFixedDecrease(assetFixedDecreaseDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDecreaseDTO updatedAssetFixedDecrease = assetFixedDecreaseService.updateAssetFixedDecrease(id, assetFixedDecreaseDTO);
            return new ResponseEntity<>(updatedAssetFixedDecrease, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to update asset fixed decrease: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssetFixedDecrease(@PathVariable Integer id) {
        try {
            assetFixedDecreaseService.deleteAssetFixedDecrease(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Failed to delete asset fixed decrease: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}