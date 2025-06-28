package com.Backend.TechnoLife.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {


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

    public Product() {

    }

    public Product( String name, Double price, int stock, String category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}