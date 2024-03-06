package com.example.exception;

public class TooLongValueException extends RuntimeException {
    public TooLongValueException(String message) {
        super(message);
    }

}
