package com.example.exception;

public class TooShortPasswordException extends RuntimeException {
    public TooShortPasswordException(String message) {
        super(message);
    }
}
