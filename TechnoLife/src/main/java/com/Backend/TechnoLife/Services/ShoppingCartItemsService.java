package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Exception.InsufficientStockException;
import com.Backend.TechnoLife.Model.*;
import com.Backend.TechnoLife.Repositories.ClientRepository;
import com.Backend.TechnoLife.Repositories.OrderRepository;
import com.Backend.TechnoLife.Repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartItemsService {

    private final ShoppingCartRepository repoShopCartItm;
    private final ClientRepository repoClient;
    private final OrderRepository repoOrder;

    @Autowired
    public ShoppingCartItemsService(ShoppingCartRepository repoShopCartItm, 
                                  ClientRepository repoClient,
                                  OrderRepository repoOrder) {
        this.repoShopCartItm = repoShopCartItm;
        this.repoClient = repoClient;
        this.repoOrder = repoOrder;
    }

    // Agregar item al carrito
    @Transactional
    public ShoppingCartItem addToCart(Client client, Product product, Integer quantity) throws InsufficientStockException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        if (product.getStock() < quantity) {
            throw new InsufficientStockException("No hay suficiente stock disponible", quantity, product.getStock());
        }

        Optional<ShoppingCartItem> existingItem = repoShopCartItm.findByClientAndProduct(client, product);
        
        if (existingItem.isPresent()) {
            ShoppingCartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException("No hay suficiente stock disponible");
            }
            item.setQuantity(newQuantity);
            return repoShopCartItm.save(item);
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(client, product, quantity);
            return repoShopCartItm.save(newItem);
        }
    }

    // Obtener todos los items del carrito de un cliente
    public List<ShoppingCartItem> getCartItems(Client client) {
        return repoShopCartItm.findByCLient(client);
    }

    // Actualizar cantidad de un item
    @Transactional
    public ShoppingCartItem updateItemQuantity(Client client, Product product, Integer newQuantity) throws InsufficientStockException {
        if (newQuantity <= 0) {
            removeFromCart(client, product);
            return null;
        }

        Optional<ShoppingCartItem> item = repoShopCartItm.findByClientAndProduct(client, product);
        if (item.isPresent()) {
            if (product.getStock() < newQuantity) {
                throw new InsufficientStockException("No hay suficiente stock disponible", newQuantity, product.getStock());
            }
            ShoppingCartItem cartItem = item.get();
            cartItem.setQuantity(newQuantity);
            return repoShopCartItm.save(cartItem);
        }
        throw new IllegalArgumentException("Item no encontrado en el carrito");
    }

    // Eliminar item del carrito
    @Transactional
    public void removeFromCart(Client client, Product product) {
        Optional<ShoppingCartItem> item = repoShopCartItm.findByClientAndProduct(client, product);
        item.ifPresent(repoShopCartItm::delete);
    }

    // Vaciar carrito
    @Transactional
    public void clearCart(Client client) {
        repoShopCartItm.deleteByClient(client);
    }

    // Calcular total del carrito
    public double calculateCartTotal(Client client) {
        List<ShoppingCartItem> items = getCartItems(client);
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Procesar la compra (checkout)
    @Transactional
    public Order checkout(Client client) {
        List<ShoppingCartItem> cartItems = getCartItems(client);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        // Verificar stock disponible
        for (ShoppingCartItem item : cartItems) {
            if (item.getProduct().getStock() < item.getQuantity()) {
                throw new IllegalStateException("No hay suficiente stock para " + item.getProduct().getName());
            }
        }

        // Crear la orden
        Order order = new Order();
        order.setClient(client);
        order.setTotal(calculateCartTotal(client));

        // Actualizar stock y crear items de la orden
        for (ShoppingCartItem item : cartItems) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            order.getItems().add(orderItem);
        }

        // Guardar la orden
        Order savedOrder = repoOrder.save(order);

        // Limpiar el carrito
        clearCart(client);

        return savedOrder;
    }
}