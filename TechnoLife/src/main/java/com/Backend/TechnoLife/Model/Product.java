package com.Backend.TechnoLife.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {


    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonProperty("nombre")
    @Column(nullable = false)
    protected String name;

    @JsonProperty("precio")
    @Column(nullable = false)
    protected Double price;

    @JsonProperty("descripcion")
    @Column(length = 1000)
    protected String description;

    @JsonProperty("stock")
    @Column(nullable = false)
    protected int stock;

    @JsonProperty("categoria")
    @Column(nullable = false)
    protected String category;


    public Product(String name, Double price, int stock, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }
}