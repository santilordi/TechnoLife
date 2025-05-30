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

    }

    public Tablet(String name, Double price, int stock) {
        super(name, price, stock);
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