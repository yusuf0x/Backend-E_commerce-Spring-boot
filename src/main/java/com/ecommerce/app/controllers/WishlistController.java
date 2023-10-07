package com.ecommerce.app.controllers;

import com.ecommerce.app.models.Favorite;
import com.ecommerce.app.payload.request.AddToWishlist;
import com.ecommerce.app.payload.response.MessageResponse;
import com.ecommerce.app.services.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    private final FavoriteService favoriteService;

    public WishlistController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody AddToWishlist addToWishlist) {
        favoriteService.addFavorite(addToWishlist);
        return ResponseEntity.ok(new MessageResponse("Product added to wishlist successfully."));
    }

    @PostMapping("/remove/{favoriteId}")
    public ResponseEntity<?> removeFromWishlist(
            @PathVariable Long favoriteId) {
        favoriteService.removeFavorite(favoriteId);
        return ResponseEntity.ok(new MessageResponse("Product removed from wishlist successfully."));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserWishlist(@PathVariable Long userId) {
        List<Favorite> wishlist = favoriteService.getFavoriteByUser(userId);
        return ResponseEntity.ok(wishlist);
    }
}
