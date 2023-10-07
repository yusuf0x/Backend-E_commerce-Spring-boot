package com.ecommerce.app.controllers;

import com.ecommerce.app.models.Cart;
import com.ecommerce.app.payload.request.AddProductToCartRequest;
import com.ecommerce.app.payload.request.UpdateCartItemQuantityRequest;
import com.ecommerce.app.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> addProductToCart(
            @RequestBody AddProductToCartRequest addProductToCartRequest
    ) {
        Cart updatedCart = cartService.addProductToCart(addProductToCartRequest.getUserId(), addProductToCartRequest.getProductId(), addProductToCartRequest.getColor());
        return ResponseEntity.ok(updatedCart);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getLoggedUserCart(@PathVariable Long userId) {
        Cart cart = cartService.getLoggedUserCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/update-cart-item-quantity")
    public ResponseEntity<Cart> updateCartItemQuantity(
           @RequestBody UpdateCartItemQuantityRequest request) {
        Cart updatedCart = cartService.updateCartItemQuantity(
                request.getUserId(),
                request.getItemId(),
                request.getNewQuantity()
        );
        return ResponseEntity.ok(updatedCart);
    }
    @PostMapping("/apply-coupon")
    public ResponseEntity<Cart> applyCoupon(
            @RequestParam Long userId,
            @RequestParam String couponCode) {
        Cart updatedCart = cartService.applyCoupon(userId, couponCode);
        return ResponseEntity.ok(updatedCart);
    }

}
