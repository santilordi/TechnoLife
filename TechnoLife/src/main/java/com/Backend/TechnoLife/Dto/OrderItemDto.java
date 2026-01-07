package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}

