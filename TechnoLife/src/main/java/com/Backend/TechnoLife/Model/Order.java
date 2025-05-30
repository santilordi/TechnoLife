package com.Backend.TechnoLife.Model;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "orders") // cambiado a "orders" porque "order" es palabra reservada en SQL
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private Double totalAmount;

    public Order() {
        this.status = OrderStatus.PENDING;
        this.totalAmount = 0.0;
    }

    public Order(Client client) {
        this.client = client;
        this.status = OrderStatus.PENDING;
        this.totalAmount = 0.0;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<OrderItem> getItems() {
        return items;
    }
}

enum OrderStatus {
    PENDING,
    COMPLETED,
    CANCELLED
}