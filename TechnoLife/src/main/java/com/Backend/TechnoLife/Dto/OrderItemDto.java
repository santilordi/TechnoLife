package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.OrderItem;

public class OrderItemDto {
    private Long id;
    private String nombre;
    private int cantidad;
    private double precio;

    public OrderItemDto(OrderItem item) {
        this.id = item.getId();
        this.nombre = item.getProduct().getName();
        this.cantidad = item.getQuantity();
        this.precio = item.getPrice(); // o item.getProduct().getPrice() si preferís
    }

    public OrderItemDto() {}

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

