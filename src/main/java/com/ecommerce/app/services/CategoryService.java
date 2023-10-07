package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.BrandNotFoundException;
import com.ecommerce.app.models.Category;
import com.ecommerce.app.repositories.CategoryRepository;
import com.ecommerce.app.utils.SlugGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, FileService fileService) {
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () ->  new BrandNotFoundException("Category Not Found with id = "+id)
        );
    }
    public Category createCategory(Category category, MultipartFile image) throws IOException {
        String fileName = fileService.uploadImage("/categories",image);
        category.setImage(fileName);
        category.setSlug(SlugGenerator.generateSlug(category.getName()));
        return categoryRepository.save(category);
    }
    public Category updateCategory(Long id, Category updateCategory) {
        Category category = getCategoryById(id);
        category.setName(updateCategory.getName());
        category.setSlug(SlugGenerator.generateSlug(updateCategory.getName()));
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
