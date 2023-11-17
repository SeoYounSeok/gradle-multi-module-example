package com.example.api.reactor.user.exceptions;

public class DataDuplicationException extends IllegalArgumentException {
    public DataDuplicationException(String msg) {
        super(msg + " is duplicated");
    }
}