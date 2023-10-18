package com.ecommerce.app.controllers;

import com.ecommerce.app.models.Brand;
import com.ecommerce.app.models.Category;
import com.ecommerce.app.models.Product;
import com.ecommerce.app.models.User;
import com.ecommerce.app.payload.response.MessageResponse;
import com.ecommerce.app.services.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/uploads")
public class UploadsController {
    private final FileService fileService;
    private final UserService userService;
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    @Value("${upload.path}")
    private String UPLOAD_DIR;

    public UploadsController(FileService fileService, UserService userService, ProductService productService, BrandService brandService, CategoryService categoryService) {
        this.fileService = fileService;
        this.userService = userService;
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }
    @GetMapping("/users/{userId}/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable Long userId, @PathVariable String imageName) {
        try {
            User user = userService.getUserById(userId);
            if(user.getPerson().getImage() == imageName) {
                try {
                    Path imagePath = Paths.get(UPLOAD_DIR + "/users/", imageName);
                    Resource resource = new UrlResource(imagePath.toUri());
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(resource);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
                }
            }else {
                return null;
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: "+e.getMessage()));
        }
    }
    @GetMapping(value = "/products/{productId}/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getProductImage(
            @PathVariable Long  productId,
            @PathVariable String image,
            HttpServletResponse response) throws IOException {
        Product product = productService.getProductById(productId);
        InputStream resource = fileService.getResource("/products/" + product.getCodeProduct(), image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    @GetMapping(value = "/brands/{brandId}/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getBrandImage(
            @PathVariable Long  brandId,
            @PathVariable String image,
            HttpServletResponse response) throws IOException {
        Brand brand = brandService.getBrandById(brandId);
        InputStream resource = fileService.getResource("/brands/", image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    @GetMapping(value = "/categories/{categoryId}/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getCategoryImage(
            @PathVariable Long  categoryId,
            @PathVariable String image,
            HttpServletResponse response) throws IOException {
        Category category = categoryService.getCategoryById(categoryId);
        InputStream resource = fileService.getResource("/categories/", image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
