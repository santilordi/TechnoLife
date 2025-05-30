package com.Backend.TechnoLife.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "tablets")
@PrimaryKeyJoinColumn(name = "product_id")
public class Tablet extends Product {
    
    @Column(name = "screen_size")
    private Double screenSize;

    @Column(name = "ram")
    private String ram;

    public Tablet() {
        // Constructor vacío necesario para JPA
    }

    public Tablet(Long id, String name, Double price, int stock) {
        super(id, name, price, stock);
    }

    // Constructor con todos los campos
    public Tablet(Long id, String name, Double price, int stock,
                 Double screenSize, String storage, String ram,
                 String batteryCapacity, String operatingSystem,
                 Boolean hasCellular) {
        super(id, name, price, stock);
        this.screenSize = screenSize;
        this.ram = ram;
    }

    // Getters y Setters
    public Double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Double screenSize) {
        this.screenSize = screenSize;
    }


    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

}