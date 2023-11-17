package com.example.api.reactor.user.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String msg) {
        super(msg + " is not found ");
    }
}