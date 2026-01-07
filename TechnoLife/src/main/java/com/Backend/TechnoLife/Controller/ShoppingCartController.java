package com.Backend.TechnoLife.Controller;

import com.Backend.TechnoLife.Exception.InsufficientStockException;
import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Order;
import com.Backend.TechnoLife.Model.Product;
import com.Backend.TechnoLife.Model.ShoppingCartItem;
import com.Backend.TechnoLife.Services.ClientService;
import com.Backend.TechnoLife.Services.ShoppingCartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartItemsService cartService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getCart(@PathVariable Long clientId) {
        Client client = clientService.obtenerClientEntityById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        List<ShoppingCartItem> items = cartService.getCartItems(client);
        double total = cartService.calculateCartTotal(client);

        return ResponseEntity.ok(Map.of(
                "items", items,
                "total", total
        ));
    }

    @GetMapping("/{clientId}/total")
    public ResponseEntity<?> getCartTotal(@PathVariable Long clientId) {
        Client client = clientService.obtenerClientEntityById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of(
                "total", cartService.calculateCartTotal(client)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCartItem request) {
        try {
            ShoppingCartItem item = cartService.addToCart(
                    request.getClient(),
                    request.getProduct(),
                    request.getQuantity()
            );

            double total = cartService.calculateCartTotal(request.getClient());

            return ResponseEntity.ok(Map.of(
                    "item", item,
                    "total", total
            ));
        } catch (IllegalArgumentException | InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{clientId}/items/{productId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long clientId,
            @PathVariable Long productId) {

        Client client = clientService.obtenerClientEntityById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        Product product = new Product();
        product.setId(productId);

        cartService.removeFromCart(client, product);
        double total = cartService.calculateCartTotal(client);

        return ResponseEntity.ok(Map.of("total", total));
    }

    @DeleteMapping("/{clientId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long clientId) {
        Client client = clientService.obtenerClientEntityById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.clearCart(client);
        return ResponseEntity.ok(Map.of("total", 0.0));
    }

    @PostMapping("/{clientId}/checkout")
    public ResponseEntity<?> checkout(@PathVariable Long clientId) {
        Client client = clientService.obtenerClientEntityById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        Order order = cartService.checkout(client);
        return ResponseEntity.ok(order);
    }
}
