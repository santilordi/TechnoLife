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
        Client client = clientService.obtenerClientById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        
        List<ShoppingCartItem> items = cartService.getCartItems(client);
        double total = cartService.calculateCartTotal(client);
        
        Map<String, Object> response = Map.of(
            "items", items,
            "total", total
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clientId}/total")
    public ResponseEntity<?> getCartTotal(@PathVariable Long clientId) {
        Client client = clientService.obtenerClientById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        
        double total = cartService.calculateCartTotal(client);
        return ResponseEntity.ok(Map.of("total", total));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCartItem request) {
        try {
            ShoppingCartItem item = cartService.addToCart(
                request.getClient(), 
                request.getProduct(), 
                request.getQuantity()
            );
            
            // Calcular y devolver el nuevo total
            double newTotal = cartService.calculateCartTotal(request.getClient());
            
            Map<String, Object> response = Map.of(
                "item", item,
                "total", newTotal
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{clientId}/update")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long clientId,
            @RequestBody ShoppingCartItem request) {
        try {
            Client client = clientService.obtenerClientById(clientId);
            if (client == null) {
                return ResponseEntity.notFound().build();
            }

            ShoppingCartItem updatedItem = cartService.updateItemQuantity(
                client,
                request.getProduct(),
                request.getQuantity()
            );

            double newTotal = cartService.calculateCartTotal(client);
            
            Map<String, Object> response = Map.of(
                "item", updatedItem,
                "total", newTotal
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{clientId}/items/{productId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long clientId,
            @PathVariable Long productId) {
        Client client = clientService.obtenerClientById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        Product productRef = new Product() {
            @Override
            public void setId(Long id) {
                super.setId(id);
            }
        };
        productRef.setId(productId);
        cartService.removeFromCart(client, productRef);
        double newTotal = cartService.calculateCartTotal(client);
        
        return ResponseEntity.ok(Map.of("total", newTotal));
    }

    @DeleteMapping("/{clientId}/clear")
    public ResponseEntity<?> clearCart(@PathVariable Long clientId) {
        Client client = clientService.obtenerClientById(clientId);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        cartService.clearCart(client);
        return ResponseEntity.ok(Map.of("total", 0.0));
    }

    @PostMapping("/{clientId}/checkout")
    public ResponseEntity<?> checkout(@PathVariable Long clientId) {
        try {
            Client client = clientService.obtenerClientById(clientId);
            if (client == null) {
                return ResponseEntity.notFound().build();
            }

            Order order = cartService.checkout(client);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}