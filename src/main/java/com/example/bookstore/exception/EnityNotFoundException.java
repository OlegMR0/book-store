package com.example.bookstore.exception;

public class EnityNotFoundException extends RuntimeException {
    public EnityNotFoundException() {
        super();
    }

    public EnityNotFoundException(String message) {
        super(message);
    }

    public EnityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
