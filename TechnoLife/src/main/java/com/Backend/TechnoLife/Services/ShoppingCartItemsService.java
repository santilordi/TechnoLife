package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Exception.InsufficientStockException;
import com.Backend.TechnoLife.Model.*;
import com.Backend.TechnoLife.Repositories.OrderRepository;
import com.Backend.TechnoLife.Repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartItemsService {

    private final ShoppingCartRepository repoShopCartItm;
    private final OrderRepository repoOrder;

    @Autowired
    public ShoppingCartItemsService(
            ShoppingCartRepository repoShopCartItm,
            OrderRepository repoOrder) {

        this.repoShopCartItm = repoShopCartItm;
        this.repoOrder = repoOrder;
    }

    // =========================
    // ADD TO CART
    // =========================
    @Transactional
    public ShoppingCartItem addToCart(Client client, Product product, Integer quantity)
            throws InsufficientStockException {

        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        if (product.getStock() < quantity) {
            throw new InsufficientStockException(
                    "No hay suficiente stock disponible",
                    quantity,
                    product.getStock()
            );
        }

        Optional<ShoppingCartItem> existingItem =
                repoShopCartItm.findByClientAndProduct(client, product);

        if (existingItem.isPresent()) {
            ShoppingCartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;

            if (product.getStock() < newQuantity) {
                throw new InsufficientStockException(
                        "No hay suficiente stock disponible",
                        newQuantity,
                        product.getStock()
                );
            }

            item.setQuantity(newQuantity);
            return repoShopCartItm.save(item);
        }

        ShoppingCartItem newItem = new ShoppingCartItem(client, product, quantity);
        return repoShopCartItm.save(newItem);
    }

    // =========================
    // GET CART ITEMS
    // =========================
    public List<ShoppingCartItem> getCartItems(Client client) {
        return repoShopCartItm.findByClient(client);
    }

    // =========================
    // UPDATE QUANTITY
    // =========================
    @Transactional
    public ShoppingCartItem updateItemQuantity(
            Client client,
            Product product,
            Integer newQuantity) throws InsufficientStockException {

        if (newQuantity <= 0) {
            removeFromCart(client, product);
            return null;
        }

        Optional<ShoppingCartItem> item =
                repoShopCartItm.findByClientAndProduct(client, product);

        if (item.isEmpty()) {
            throw new IllegalArgumentException("Item no encontrado en el carrito");
        }

        if (product.getStock() < newQuantity) {
            throw new InsufficientStockException(
                    "No hay suficiente stock disponible",
                    newQuantity,
                    product.getStock()
            );
        }

        ShoppingCartItem cartItem = item.get();
        cartItem.setQuantity(newQuantity);
        return repoShopCartItm.save(cartItem);
    }

    // =========================
    // REMOVE ITEM
    // =========================
    @Transactional
    public void removeFromCart(Client client, Product product) {
        repoShopCartItm.findByClientAndProduct(client, product)
                .ifPresent(repoShopCartItm::delete);
    }

    // =========================
    // CLEAR CART
    // =========================
    @Transactional
    public void clearCart(Client client) {
        repoShopCartItm.deleteByClient(client);
    }

    // =========================
    // CALCULATE TOTAL
    // =========================
    public double calculateCartTotal(Client client) {
        return getCartItems(client)
                .stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // =========================
    // CHECKOUT
    // =========================
    @Transactional
    public Order checkout(Client client) {

        List<ShoppingCartItem> cartItems = getCartItems(client);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        // Verificar stock
        for (ShoppingCartItem item : cartItems) {
            if (item.getProduct().getStock() < item.getQuantity()) {
                throw new IllegalStateException(
                        "No hay suficiente stock para " +
                                item.getProduct().getName()
                );
            }
        }

        Order order = new Order();
        order.setClient(client);
        order.setTotal(calculateCartTotal(client));

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

        Order savedOrder = repoOrder.save(order);
        clearCart(client);

        return savedOrder;
    }
}
