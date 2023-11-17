package com.example.api.reactor.user.exceptions;

public class PasswordNotMatchException extends IllegalArgumentException {
    public PasswordNotMatchException() {
        super();
    }

    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}

