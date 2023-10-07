package com.ecommerce.app.controllers;
import com.ecommerce.app.models.Product;
import com.ecommerce.app.models.Review;
import com.ecommerce.app.payload.request.CreateProduct;
import com.ecommerce.app.payload.request.CreateReview;
import com.ecommerce.app.payload.request.UpdateProduct;
import com.ecommerce.app.services.ProductService;
import com.ecommerce.app.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ReviewService reviewService;

    @Autowired
    public ProductController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
       return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(@RequestPart CreateProduct product, @RequestPart("images") List<MultipartFile> images, @RequestPart("imageCover") MultipartFile imageCover){
        try {
            Product createdProduct = productService.createProduct(product, images, imageCover);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch (Exception e){
//            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody UpdateProduct product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
        }catch (Exception e){
//            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // reviews
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<Review>> getAllReviewsForProducts(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getAllReviewsForProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return new ResponseEntity<>(
                reviewService.getReviewById(reviewId),HttpStatus.OK
        );
    }

    @PostMapping("/{productId}/reviews/")
    public ResponseEntity<?> createReview(@PathVariable Long productId , @RequestBody CreateReview createReview) {
        try {
            Review createdReview = reviewService.createReview(productId, createReview);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{productId}/reviews/{reviewId}/")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @PathVariable Long productId, @RequestBody CreateReview review) {
        Review updatedReview = reviewService.updateReview(reviewId,productId, review);
        return updatedReview != null ? ResponseEntity.ok(updatedReview) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}
