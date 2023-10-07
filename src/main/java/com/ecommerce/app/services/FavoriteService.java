package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.FavoriteNotFoundException;
import com.ecommerce.app.models.Favorite;
import com.ecommerce.app.models.Product;
import com.ecommerce.app.models.User;
import com.ecommerce.app.payload.request.AddToWishlist;
import com.ecommerce.app.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, ProductService productService, UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public Favorite addFavorite(AddToWishlist request) {
        User user = userService.getUserById(request.getUserId());
        Product product = productService.getProductById(request.getProductId());
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setProduct(product);
        return favoriteRepository.save(favorite);
    }
    public List<Favorite> getFavoriteByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
    public  Favorite getFavoriteById(Long id){
        return favoriteRepository.findById(id).orElseThrow(
                () -> new FavoriteNotFoundException("Favorite Not FOund with id ="+id)
        );
    }
    public void removeFavorite(Long favoriteId) {
        Favorite favorite = getFavoriteById(favoriteId);
        favoriteRepository.delete(favorite);
    }
}
