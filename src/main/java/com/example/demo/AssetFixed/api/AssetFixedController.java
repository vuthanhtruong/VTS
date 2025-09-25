package com.example.demo.AssetFixed.api;

import com.example.demo.AssetFixed.dto.AssetFixedDTO;
import com.example.demo.AssetFixed.service.AssetFixedSerivce;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-fixed")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class AssetFixedController {

    private final AssetFixedSerivce assetFixedService;

    public AssetFixedController(AssetFixedSerivce assetFixedService) {
        this.assetFixedService = assetFixedService;
    }

    @PostMapping
    public ResponseEntity<?> createAssetFixed(@Valid @RequestBody AssetFixedDTO assetFixedDTO) {
        List<String> errors = assetFixedService.validateAssetFixed(assetFixedDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDTO createdAssetFixed = assetFixedService.createAssetFixed(assetFixedDTO);
            return new ResponseEntity<>(createdAssetFixed, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create asset fixed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetFixedById(@PathVariable Integer id) {
        try {
            AssetFixedDTO assetFixed = assetFixedService.getAssetFixedById(id);
            return new ResponseEntity<>(assetFixed, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssetFixed() {
        try {
            List<AssetFixedDTO> assetFixeds = assetFixedService.getAllAssetFixed();
            return new ResponseEntity<>(assetFixeds, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve asset fixeds: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssetFixed(@PathVariable Integer id, @Valid @RequestBody AssetFixedDTO assetFixedDTO) {
        List<String> errors = assetFixedService.validateAssetFixed(assetFixedDTO);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>("Validation failed: " + String.join(", ", errors), HttpStatus.BAD_REQUEST);
        }
        try {
            AssetFixedDTO updatedAssetFixed = assetFixedService.updateAssetFixed(id, assetFixedDTO);
            return new ResponseEntity<>(updatedAssetFixed, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update asset fixed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssetFixed(@PathVariable Integer id) {
        try {
            assetFixedService.deleteAssetFixed(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete asset fixed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}