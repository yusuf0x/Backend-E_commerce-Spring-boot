package com.ecommerce.app.controllers;

import com.ecommerce.app.models.Category;
import com.ecommerce.app.models.SubCategory;
import com.ecommerce.app.services.CategoryService;
import com.ecommerce.app.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/subcategories")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;
    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> getAllCategories() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/{subcategoryId}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long subcategoryId) {
       return new ResponseEntity<>(
               subCategoryService.getSubCategoryById(subcategoryId),HttpStatus.OK
       );
    }

    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubCategory);
    }

    @PutMapping("/{subcategoryId}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long subcategoryId, @RequestBody SubCategory subCategory) {
        SubCategory updatedSubCategory = subCategoryService.updateSubCategory(subcategoryId, subCategory);
        return updatedSubCategory != null ? ResponseEntity.ok(updatedSubCategory) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{subcategoryId}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long subcategoryId) {
        subCategoryService.deleteSubCategory(subcategoryId);
        return ResponseEntity.noContent().build();
    }
}
