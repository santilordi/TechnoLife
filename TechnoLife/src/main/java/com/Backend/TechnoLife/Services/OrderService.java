package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Dto.OrderDto;
import com.Backend.TechnoLife.Dto.OrderItemDto;
import com.Backend.TechnoLife.Dto.ProductDto;
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

import java.util.ArrayList;
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
    public Order createOrderFromShoppingCart(OrderDto orderDto) throws CustomerNotFoundException, EmptyOrderException {
        // Ejemplo básico:
        Order order = new Order();
        List<OrderItem> items = new ArrayList<>();
        Double total = 0.0;
        for (OrderItemDto prod : orderDto.getProductos()) {
            Product producto = repoProduct.findById(prod.getId()).orElse(null);
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado: " + prod.getNombre());
            }
            if (producto.getStock() < prod.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para " + producto.getName());
            }

            total += producto.getPrice() * prod.getCantidad();
            producto.setStock(producto.getStock() - prod.getCantidad());
            repoProduct.save(producto);

            OrderItem item = new OrderItem();
            item.setProduct(producto);
            item.setQuantity(prod.getCantidad());
            item.setPrice(producto.getPrice());
            items.add(item);
        }
        order.setItems(items);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(total);
        return repoOrder.save(order);
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

    @Transactional
    public void deleteOrder (Long orderID) throws OrderException {
        Order order = repoOrder.findById(orderID)
                .orElseThrow(() -> new OrderException("Orden no encontrada"));

        for (OrderItem item : order.getItems()){
            Product product = item.getProduct();
            if (product != null && item.getQuantity() != null){
                product.setStock(product.getStock() + item.getQuantity());
                repoProduct.save(product);
            }
        }

        repoOrder.delete(order);
    }
}