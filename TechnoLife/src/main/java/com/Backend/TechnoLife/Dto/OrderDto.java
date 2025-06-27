package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrderDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<OrderItemDto> getProductos() {
        return productos;
    }

    public void setProductos(List<OrderItemDto> productos) {
        this.productos = productos;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }
}

