package com.Backend.TechnoLife.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "computers")
@PrimaryKeyJoinColumn(name = "product_id")
public class Computer extends Product {
    
    @Column(name = "processor")
    private String processor;
    
    @Column(name = "ram")
    private String ram;
    
    @Column(name = "storage")
    private String storage;
    
    @Column(name = "graphics_card")
    private String graphicsCard;

    public Computer(Long id, String name, Double price, int stock) {
        super(id, name, price, stock);
    }

    // Getters y setters para los nuevos campos
    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }
}