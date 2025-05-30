package com.Backend.TechnoLife.Controller;

import com.Backend.TechnoLife.Exception.InsufficientStockException;
import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Order;
import com.Backend.TechnoLife.Model.ShoppingCartItem;
import com.Backend.TechnoLife.Services.ShoppingCartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartItemsService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCartItem request) {
        try {
            ShoppingCartItem item = cartService.addToCart(
                request.getClient(), 
                request.getProduct(), 
                request.getQuantity()
            );
            return ResponseEntity.ok(item);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Client client) {
        try {
            Order order = cartService.checkout(client);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}