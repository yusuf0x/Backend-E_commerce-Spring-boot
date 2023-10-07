package com.ecommerce.app.services;
import com.ecommerce.app.exceptions.SubCategoryNotFoundException;
import com.ecommerce.app.models.Category;
import com.ecommerce.app.models.SubCategory;
import com.ecommerce.app.repositories.CategoryRepository;
import com.ecommerce.app.repositories.SubCategoryRepository;
import com.ecommerce.app.utils.SlugGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<SubCategory> getAllSubCategories(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(null);
        return subCategoryRepository.findByCategory(category);
    }
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id).orElseThrow(
                () -> new SubCategoryNotFoundException("SubCategory Not Found with id = "+id)
        );
    }

    public SubCategory createSubCategory(Long categoryId,SubCategory subCategory) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(null);
        subCategory.setCategory(category);
        subCategory.setSlug(SlugGenerator.generateSlug(subCategory.getName()));
        return subCategoryRepository.save(subCategory);
    }
    public SubCategory createSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public SubCategory updateSubCategory(Long id, SubCategory updateSubCategory) {
        SubCategory  subCategory = getSubCategoryById(id);
        subCategory.setName(updateSubCategory.getName());
        subCategory.setSlug(SlugGenerator.generateSlug(updateSubCategory.getName()));
        return subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(Long id) {
        SubCategory subCategory = getSubCategoryById(id);
        subCategoryRepository.delete(subCategory);
    }
}