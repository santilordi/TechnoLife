package com.Backend.TechnoLife.Exception;

public class InvalidEmailException extends CustomerException {
    public InvalidEmailException(String email) {
        super("Formato de email invalido: " + email);
    }
}
