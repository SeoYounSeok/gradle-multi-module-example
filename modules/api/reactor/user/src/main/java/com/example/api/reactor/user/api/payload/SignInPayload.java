package com.example.api.reactor.user.api.payload;

import javax.validation.constraints.NotBlank;

import com.example.api.reactor.user.manage.dto.UserDto;

import lombok.Data;

@Data
public class SignInPayload {

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    public UserDto toDto() {
        UserDto dto = new UserDto();
        dto.setAccount(account);
        dto.setPassword(password);
        return dto;
    }
}

