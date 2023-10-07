package com.ecommerce.app.controllers;

import com.ecommerce.app.models.Brand;
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
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @Autowired
    public CategoryController(CategoryService categoryService, SubCategoryService subCategoryService) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(
                categoryService.getCategoryById(id), HttpStatus.OK
        );
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createCategory(@RequestPart Category category, @RequestPart MultipartFile image) {
        try {
            Category createdCategory = categoryService.createCategory(category,image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    // subcategory

    @GetMapping("/{categoryId}/subcategories/")
    public ResponseEntity<List<SubCategory>> getAllSubCategoriesofCategory(@PathVariable Long categoryId) {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories(categoryId);
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/{categoryId}/subcategories/{subcategoryId}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long categoryId,@PathVariable Long subcategoryId) {
        return new ResponseEntity<>(
                subCategoryService.getSubCategoryById(categoryId), HttpStatus.OK
        );
    }

    @PostMapping("/{categoryId}/subcategories/")
    public ResponseEntity<SubCategory> createSubCategory(@PathVariable Long categoryId, @RequestBody SubCategory subCategory) {
        SubCategory createdSubCategory = subCategoryService.createSubCategory(categoryId,subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubCategory);
    }

    @PutMapping("/{categoryId}/subcategories/{subcategoryId}/")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long id, @RequestBody SubCategory subCategory) {
        SubCategory updatedSubCategory = subCategoryService.updateSubCategory(id, subCategory);
        return updatedSubCategory != null ? ResponseEntity.ok(updatedSubCategory) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{categoryId}/subcategories/{subcategoryId}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }
}
