package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.OrderItem;

public class OrderItemDto {
    private String nombre;
    private int cantidad;
    private double precio;

    public OrderItemDto(OrderItem item) {
        this.nombre = item.getProduct().getName();
        this.cantidad = item.getQuantity();
        this.precio = item.getPrice(); // o item.getProduct().getPrice() si preferís
    }

    // Getters si los necesitás
}

