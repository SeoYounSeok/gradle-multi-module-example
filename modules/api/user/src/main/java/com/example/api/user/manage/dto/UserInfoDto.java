package com.example.api.user.manage.dto;

import java.time.LocalDateTime;

import com.example.persistence.domain.UserInfoModel;

import lombok.Data;

@Data
public class UserInfoDto implements UserInfoModel {
    private Long infoId;
    private String userName;
    private LocalDateTime birthDay;
    private String userId;
    private UserDto user;

    public UserInfoDto(UserInfoModel model) {
        this.infoId = model.getInfoId();
        this.userName = model.getUserName();
        this.birthDay = model.getBirthDay();
        this.userId = model.getUserId();
        this.user = new UserDto(model.getUser());
    }
    
    @Override
    public Long getInfoId() {
        return this.infoId;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public LocalDateTime getBirthDay() {
        return this.birthDay;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public UserDto getUser() {
        return this.user;
    }
    
}
