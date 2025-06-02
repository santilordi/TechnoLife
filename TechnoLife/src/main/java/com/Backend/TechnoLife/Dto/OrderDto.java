package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {
    private Long id;
    private String estado;
    private Double total;
    private List<OrderItemDto> productos;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.estado = order.getStatus().name(); // Enum a texto
        this.total = order.getTotal();
        this.productos = order.getItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }

    // Getters si vas a usar con @RestController
}

