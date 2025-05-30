package com.Backend.TechnoLife.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "phones")
@PrimaryKeyJoinColumn(name = "product_id")
public class Phone extends Product {
    
    @Column(name = "screen_size")
    private Double screenSize;
    
    @Column(name = "storage")
    private String storage;
    
    @Column(name = "ram")
    private String ram;


    public Phone() {
    }

    public Phone(Long id, String name, Double price, int stock) {
        super(id, name, price, stock);
    }

    public Double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Double screenSize) {
        this.screenSize = screenSize;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

}