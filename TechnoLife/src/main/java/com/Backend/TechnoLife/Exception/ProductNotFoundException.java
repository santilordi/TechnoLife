package com.Backend.TechnoLife.Exception;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(int id) {
        super("Producto con ID " + id + " no encontrado.");
    }

    public ProductNotFoundException(String name) {
        super("Producto con nommbre '" + name + "' no encontrado.");
    }
}
