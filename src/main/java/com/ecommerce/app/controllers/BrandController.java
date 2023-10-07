package com.ecommerce.app.controllers;

import com.ecommerce.app.models.Brand;
import com.ecommerce.app.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;
    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return new ResponseEntity<>(
                brandService.getBrandById(id), HttpStatus.OK
        );
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createBrand(@RequestPart Brand brand,@RequestPart("image") MultipartFile image) throws IOException {
        try {
            Brand createdBrand = brandService.createBrand(brand,image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        Brand updatedBrand = brandService.updateBrand(id, brand);
        return updatedBrand != null ? ResponseEntity.ok(updatedBrand) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
