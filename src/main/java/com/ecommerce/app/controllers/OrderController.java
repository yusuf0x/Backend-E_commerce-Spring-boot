package com.ecommerce.app.controllers;

import com.ecommerce.app.models.*;
import com.ecommerce.app.services.CartService;
import com.ecommerce.app.services.OrderService;
import com.ecommerce.app.services.ProductService;
import com.ecommerce.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    public OrderController(OrderService orderService, CartService cartService, ProductService productService, UserService userService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrder(@PathVariable Long id) {
        return new ResponseEntity<>(
                orderService.getOrderById(id), HttpStatus.OK
        );
    }
    @PostMapping("/{cartId}/{userId}")
    public ResponseEntity<Order> createOrder(
            @PathVariable Long cartId,
            @PathVariable Long userId,
            @RequestBody ShippingAddress shippingAddress) {
        Double taxPrice = 0.0;
        Double shippingPrice = 0.0;
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // 2) Get order price based on cart price (check if a coupon applies)
        Double cartPrice = cart.getTotalPriceAfterDiscount() != null ? cart.getTotalPriceAfterDiscount() : cart.getTotalCartPrice();
        Double totalOrderPrice = cartPrice + taxPrice + shippingPrice;
        // 3) Create an order with default paymentMethodType (cash)
        Order order = new Order();
        User user = userService.getUserById(userId);
        order.setUser(user);
        List<OrderItem> orderItems = cart.getCartItems().stream().map(
                item -> new OrderItem(null,item.getProduct(),item.getQuantity(),item.getColor(),item.getPrice())
        ).toList();
        order.setOrderItems(orderItems);
        order.setShippingAddress(shippingAddress);
        order.setTotalOrderPrice(totalOrderPrice);
        order = orderService.createOrder(order);
        // 4) After creating the order, decrement product quantity and increment product sold
        if (order != null) {
            for (CartItem item : cart.getCartItems()) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();
                productService.updateProductQuantityAndSold(product.getId(), -quantity, quantity);
            }
            cartService.deleteCart(cartId);
        }
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @PutMapping("/{orderId}/pay")
    public ResponseEntity<Order> updateOrderToPaid(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setIsPaid(true);
        order.setPaidAt(new Date());
        order = orderService.createOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> updateOrderToDelivered(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        order.setIsDelivered(true);
        order.setDeliveredAt(new Date());
        order = orderService.createOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
