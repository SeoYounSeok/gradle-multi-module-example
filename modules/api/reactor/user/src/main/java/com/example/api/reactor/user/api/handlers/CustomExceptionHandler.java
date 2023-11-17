package com.example.api.reactor.user.api.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.api.reactor.user.exceptions.DataDuplicationException;
import com.example.api.reactor.user.exceptions.DataNotFoundException;
import com.example.api.reactor.user.exceptions.PasswordNotMatchException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DataDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleDataDuplicationException(DataDuplicationException e) {
        ErrorResponse error = ErrorResponse.builder()
                .code("409 CONFLICT")
                .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
        ErrorResponse error = ErrorResponse.builder()
                .code("404 NOT-FOUND")
                .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordNotMatchException(PasswordNotMatchException e) {
        ErrorResponse error = ErrorResponse.builder()
                .code("401 UNAUTHORIZED")
                .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
    
}
