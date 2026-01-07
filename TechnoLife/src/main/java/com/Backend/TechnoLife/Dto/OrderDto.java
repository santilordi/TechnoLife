package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor

public class OrderDto {
    private Long id;
    private String estado;
    private Double total;
    private List<OrderItemDto> productos;

    @JsonProperty("client")
    private ClientDto client;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.estado = order.getStatus().name(); // Enum a texto
        this.total = order.getTotal();
        this.productos = order.getItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
        this.client = order.getClient() != null ? new ClientDto(order.getClient()) : null;
    }

}

