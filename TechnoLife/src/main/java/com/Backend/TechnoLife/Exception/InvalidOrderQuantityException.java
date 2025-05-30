package com.Backend.TechnoLife.Exception;

public class InvalidOrderQuantityException extends OrderException {
    public InvalidOrderQuantityException(int quantity) {
        super("Cantidad del producto invalido: " + quantity + ". Debe ser mayor a cero.");
    }
}
