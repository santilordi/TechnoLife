package com.Backend.TechnoLife.Exception;

public class InvalidPriceException extends ProductException {
    public InvalidPriceException(double price) {
        super("Precio invalido: " + price + ". El precio deberia ser mayor a cero.");
    }
}
