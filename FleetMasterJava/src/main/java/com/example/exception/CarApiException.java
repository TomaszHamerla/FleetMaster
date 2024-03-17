package com.example.exception;

public class CarApiException extends RuntimeException {
    public CarApiException(String message) {
        super(message);
    }
}
