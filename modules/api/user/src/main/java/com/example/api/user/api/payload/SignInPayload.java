package com.example.api.user.api.payload;

import com.example.api.user.manage.dto.UserDto;

import lombok.Data;

@Data
public class SignInPayload {
    private String account;
    private String password;

    public UserDto toDto() {
        UserDto dto = new UserDto();
        dto.setAccount(account);
        dto.setPassword(password);
        return dto;
    }
}
