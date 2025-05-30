package com.Backend.TechnoLife.Exception;

public class EmptyOrderException extends OrderException {
    public EmptyOrderException() {
        super("No se puede crear una orden sin productos.");
    }
}
