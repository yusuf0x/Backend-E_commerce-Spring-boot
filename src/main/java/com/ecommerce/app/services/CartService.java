package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.CartItemNotFoundException;
import com.ecommerce.app.exceptions.CartNotFoundException;
import com.ecommerce.app.exceptions.CouponNotFoundException;
import com.ecommerce.app.exceptions.SubCategoryNotFoundException;
import com.ecommerce.app.models.*;

import com.ecommerce.app.repositories.CartRepository;
import com.ecommerce.app.repositories.CouponRepository;
import com.ecommerce.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;



    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService, UserRepository userRepository, CouponRepository couponRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new CartNotFoundException("Cart Not Found with id = "+id)
        );
    }
    public void deleteCart(Long id) {
        Cart cart = getCartById(id);
        cartRepository.delete(cart);
    }
    public Cart findByUserId(Long userId){
        return  cartRepository.findByUserId(userId)
                .orElseThrow(
                () -> new CartNotFoundException("There is no cart for this user ID: " + userId)
        );
    }
    public Cart addProductToCart(Long userId, Long productId, String color) {
        User user = userRepository.findById(userId).orElseThrow(null);
        Product product = productService.getProductById(productId);

        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCartItems(new ArrayList<>());
        }
        CartItem cartItem = findCartItemByProductIdAndColor(cart, productId, color);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setColor(color);
            cartItem.setPrice(Double.valueOf(product.getPrice().toString()));
            cartItem.setQuantity(1);
            cart.getCartItems().add(cartItem);
        }
        cart = calcTotalCartPrice(cart);
//        cart.setCreatedAt();
        return  cartRepository.save(cart);
    }
    public Cart getLoggedUserCart(Long userId) {
        return findByUserId(userId);
    }
    public Cart updateCartItemQuantity(Long userId, Long itemId, int quantity) {
        Cart cart = findByUserId(userId);
        int itemIndex = -1;
        for (int i = 0; i < cart.getCartItems().size(); i++) {
            if (cart.getCartItems().get(i).getId().equals(itemId)) {
                itemIndex = i;
                break;
            }
        }
        if (itemIndex > -1) {
            CartItem cartItem = cart.getCartItems().get(itemIndex);
            cartItem.setQuantity(quantity);
            cart.getCartItems().set(itemIndex, cartItem);
        } else {
            throw new CartItemNotFoundException("There is no item for this ID: " + itemId);
        }
        cart = calcTotalCartPrice(cart);
        return  cartRepository.save(cart);
    }
    public Cart applyCoupon(Long userId, String couponCode) {
        Cart cart = findByUserId(userId);
        Coupon coupon = couponRepository.findByNameAndExpireAfter(couponCode, new Date());
        if (coupon == null) {
            throw new CouponNotFoundException("Coupon is invalid or expired");
        }
        BigDecimal totalPrice = BigDecimal.valueOf(cart.getTotalCartPrice());
        BigDecimal discountAmount = totalPrice.multiply(coupon.getDiscount().divide(BigDecimal.valueOf(100)));
        BigDecimal totalPriceAfterDiscount = totalPrice.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
        cart.setTotalPriceAfterDiscount(Double.valueOf(totalPriceAfterDiscount.toString()));
        return cartRepository.save(cart);
    }
    public Cart calcTotalCartPrice(Cart cart) {
        double totalPrice = 0;
        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.getQuantity() * item.getPrice();
        }
        cart.setTotalCartPrice(totalPrice);
        cart.setTotalPriceAfterDiscount(null);
        return cart;
    }
    private CartItem findCartItemByProductIdAndColor(Cart cart, Long productId, String color) {
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId) && item.getColor().equals(color)) {
                return item;
            }
        }
        return null;
    }
}

