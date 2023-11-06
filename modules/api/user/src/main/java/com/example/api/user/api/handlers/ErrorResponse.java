package com.example.api.user.api.handlers;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ErrorResponse {
    private String code;
    private String message;
}
