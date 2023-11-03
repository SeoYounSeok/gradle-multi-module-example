package com.example.api.user.exceptions;

public class DataDuplicationException extends IllegalArgumentException {
    public DataDuplicationException(String msg) {
        super(msg);
    }
}
