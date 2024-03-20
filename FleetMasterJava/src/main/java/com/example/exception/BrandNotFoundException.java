package com.example.exception;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(String message) {
        super(message);
    }
}
