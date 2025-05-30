package com.Backend.TechnoLife.Exception;

public class CustomerNotFoundException extends CustomerException {
    public CustomerNotFoundException(int id) {
        super("Cliente con ID " + id + " no encontrado.");
    }

    public CustomerNotFoundException(String name) {
        super("Cliente con el nombre '" + name + "' no encontrado.");
    }
}
