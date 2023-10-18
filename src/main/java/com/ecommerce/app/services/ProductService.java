package com.ecommerce.app.services;



import com.ecommerce.app.exceptions.ProductNotFoundException;
import com.ecommerce.app.models.Category;
import com.ecommerce.app.models.Product;
import com.ecommerce.app.models.SubCategory;
import com.ecommerce.app.payload.request.CreateProduct;
import com.ecommerce.app.payload.request.UpdateProduct;
import com.ecommerce.app.repositories.ProductRepository;
import com.ecommerce.app.utils.SlugGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final FileService fileService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, FileService fileService, BrandService brandService, CategoryService categoryService, SubCategoryService subCategoryService) {
        this.productRepository = productRepository;
        this.fileService = fileService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product Not Found with id = "+id)
        );
    }

    public Product createProduct(CreateProduct createProduct, List<MultipartFile> images, MultipartFile imageCover) throws IOException {
        Product product = new Product();
        product.setCodeProduct(createProduct.getCodeProduct());
        product.setColors(createProduct.getColors());
        product.setDescription(createProduct.getDescription());
        product.setPrice(createProduct.getPrice());
        product.setPriceAfterDiscount(createProduct.getPriceAfterDiscount());
        product.setQuantity(createProduct.getQuantity());
        product.setTitle(createProduct.getTitle());
        product.setStatus(createProduct.getStatus());
        product.setSlug(SlugGenerator.generateSlug(createProduct.getTitle()));
        product.setRatingsAverage(createProduct.getRatingsAverage());
        product.setRatingsQuantity(createProduct.getRatingsQuantity());
        String image = fileService.uploadImage("/products/"+createProduct.getCodeProduct(),imageCover);
        product.setImageCover(image);
        List<String> files = images.stream().map(
                file -> {
                    try {
                        return fileService.uploadImage("/products/"+product.getCodeProduct(),file);
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
        ).toList();
        product.setImages(files);
        Category category = categoryService.getCategoryById(createProduct.getCategoryId());
        product.setCategory(category);
        if(!createProduct.getSubcategoryIds().isEmpty()) {
            List<SubCategory> subCategories = createProduct.getSubcategoryIds().stream().map(
                    id -> subCategoryService.getSubCategoryById(id)
            ).toList();
            product.setSubcategories(subCategories);
        }else{
            product.setSubcategories(Arrays.asList());
        }
        product.setBrand(brandService.getBrandById(createProduct.getBrandId()));
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, UpdateProduct updateProduct) {
        Product product = getProductById(id);
        product.setCodeProduct(updateProduct.getCodeProduct());
        product.setColors(updateProduct.getColors());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setPriceAfterDiscount(updateProduct.getPriceAfterDiscount());
        product.setQuantity(updateProduct.getQuantity());
        product.setTitle(updateProduct.getTitle());
        product.setStatus(updateProduct.getStatus());
        product.setSlug(SlugGenerator.generateSlug(updateProduct.getTitle()));
        product.setRatingsAverage(updateProduct.getRatingsAverage());
        product.setRatingsQuantity(updateProduct.getRatingsQuantity());
        Category category = categoryService.getCategoryById(updateProduct.getCategoryId());
        product.setCategory(category);
        List<SubCategory> subCategories = updateProduct.getSubcategoryIds().stream().map(
                    item -> subCategoryService.getSubCategoryById(item)
        ).toList();
        product.getSubcategories().addAll(subCategories);
        product.setBrand(brandService.getBrandById(updateProduct.getBrandId()));
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public void updateProductQuantityAndSold(Long productId, int quantityChange, int soldChange) {
        Product product = getProductById(productId);
        int currentQuantity = product.getQuantity();
        int currentSold = product.getSold();
        int newQuantity = Math.max(currentQuantity + quantityChange, 0);
        int newSold = Math.max(currentSold + soldChange, 0);
        product.setQuantity(newQuantity);
        product.setSold(newSold);
        productRepository.save(product);

    }
}
