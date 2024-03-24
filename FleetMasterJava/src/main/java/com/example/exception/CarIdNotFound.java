package com.example.exception;

public class CarIdNotFound extends RuntimeException {
    public CarIdNotFound(String message) {
        super(message);
    }
}
