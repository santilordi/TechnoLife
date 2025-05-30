package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Exception.CustomerNotFoundException;
import com.Backend.TechnoLife.Exception.EmptyOrderException;
import com.Backend.TechnoLife.Exception.OrderException;
import com.Backend.TechnoLife.Model.*;
import com.Backend.TechnoLife.Repositories.ClientRepository;
import com.Backend.TechnoLife.Repositories.OrderRepository;
import com.Backend.TechnoLife.Repositories.ProductRepository;
import com.Backend.TechnoLife.Repositories.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repoOrder;
    private final ClientRepository repoClient;
    private final ProductRepository repoProduct;
    private final ShoppingCartRepository repoCart;

    @Autowired
    public OrderService(OrderRepository repoOrder, 
                       ClientRepository repoClient, 
                       ProductRepository repoProduct, 
                       ShoppingCartRepository repoCart) {
        this.repoOrder = repoOrder;
        this.repoClient = repoClient;
        this.repoProduct = repoProduct;
        this.repoCart = repoCart;
    }

    public List<Order> obtenerOrdenes() {
        return repoOrder.findAll();
    }

    public Order obtenerOrdenById(Long id) throws OrderException {
        return repoOrder.findById(id)
                .orElseThrow(() -> new OrderException("Orden no encontrada con ID: " + id));
    }

    public List<Order> obtenerClientOrders(Long clientId) {
        return repoOrder.findByClientID(clientId);
    }

    @Transactional
    public Order createOrderFromShoppingCart(Long clientId) throws CustomerNotFoundException, EmptyOrderException {
        // Obtener cliente
        Client client = repoClient.findById((clientId))
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con ID: " + clientId));

        // Obtener items del carrito del cliente
        List<ShoppingCartItem> cartItems = repoCart.findByCLient(client);

        if (cartItems.isEmpty()) {
            throw new EmptyOrderException();
        }

        Order newOrder = new Order(client);
        double total = 0.0;

        // Convertir ShoppingCartItems a OrderItems
        for (ShoppingCartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            Integer quantity = cartItem.getQuantity();

            // Verificar stock
            if (product.getStock() < quantity) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + product.getName() +
                        ". Disponible: " + product.getStock() + 
                        ", Solicitado: " + quantity);
            }

            // Crear OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            newOrder.getItems().add(orderItem);

            // Actualizar stock
            product.setStock(product.getStock() - quantity);
            repoProduct.save(product);

            // Actualizar total
            total += product.getPrice() * quantity;
        }

        newOrder.setTotal(total);
        
        // Guardar orden
        Order savedOrder = repoOrder.save(newOrder);

        // Limpiar carrito
        repoCart.deleteByClient(client);

        return savedOrder;
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) throws OrderException {
        Order order = obtenerOrdenById(orderId);
        order.setStatus(newStatus);
        return repoOrder.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) throws OrderException {
        Order order = obtenerOrdenById(orderId);
        if (order.getStatus() == OrderStatus.PENDING) {
            // Restaurar stock
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                repoProduct.save(product);
            }
            order.setStatus(OrderStatus.CANCELLED);
            repoOrder.save(order);
        } else {
            throw new IllegalStateException("No se puede cancelar una orden que no está pendiente");
        }
    }
}