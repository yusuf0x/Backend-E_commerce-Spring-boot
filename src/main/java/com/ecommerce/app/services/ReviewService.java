package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.ReviewNotFoundException;
import com.ecommerce.app.models.Product;
import com.ecommerce.app.models.Review;
import com.ecommerce.app.models.User;
import com.ecommerce.app.payload.request.CreateReview;
import com.ecommerce.app.repositories.ProductRepository;
import com.ecommerce.app.repositories.ReviewRepository;
import com.ecommerce.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    public List<Review> getAllReviewsForProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new ReviewNotFoundException("Review Not FOund with id = "+id)
        );
    }

    public Review createReview(Long productId,CreateReview createReview) {
        Review review = new Review();
        User user = userRepository.findById(createReview.getUserId()).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        review.setUser(user);
        review.setProduct(product);
        review.setTitle(createReview.getTitle());
        review.setRatings(createReview.getRatings());
        return reviewRepository.save(review);
    }

    public Review updateReview(Long reviewId, Long productId,CreateReview updateReview) {
        Review review = getReviewById(reviewId);
        review.setTitle(updateReview.getTitle());
        review.setRatings(updateReview.getRatings());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }
}

