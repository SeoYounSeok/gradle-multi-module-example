package com.example.api.reactor.user.api.results;

import java.time.LocalDateTime;

import com.example.api.reactor.user.manage.dto.UserDto;
import com.example.api.reactor.user.manage.dto.UserInfoDto;

import lombok.Data;

@Data
public class UserResult {
    
    private String userId;
    private String account;
    private String role;
    private String userName;
    private LocalDateTime birthDay;

    public UserResult(UserDto dto) {
        this.userId = dto.getUserId();
        this.account = dto.getAccount();
        this.role = dto.getRole();

        setInfoIfPresent(dto.getUserInfo());
    }

    private void setInfoIfPresent(UserInfoDto dto) {
        if (dto == null) return;

        this.userName = dto.getUserName();
        this.birthDay = dto.getBirthDay();
    }
}

