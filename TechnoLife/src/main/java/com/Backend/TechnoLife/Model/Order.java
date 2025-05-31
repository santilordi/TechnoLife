package com.Backend.TechnoLife.Model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "orders")
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

    @Column(nullable = false)
    private Double totalAmount;

    public Order() {
        this.status = OrderStatus.PENDING;
        this.totalAmount = 0.0;
    }

    public Order(Client client) {
        this();
        this.client = client;
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

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotal(){
        return totalAmount;
    }

    public void setTotal(double v) {
        this.totalAmount = v;
    }
}

