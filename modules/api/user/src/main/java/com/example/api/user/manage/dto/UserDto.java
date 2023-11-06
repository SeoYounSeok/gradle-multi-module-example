package com.example.api.user.manage.dto;

import com.example.persistence.domain.UserModel;

import lombok.Setter;
import lombok.ToString;

@Setter @ToString
public class UserDto implements UserModel {

    private String userId;
    private String account;
    private String password;
    private String role;
    private Long infoId;
    private UserInfoDto userInfo;

    public UserDto() {}

    public UserDto(UserModel model) {
        this.userId = model.getUserId();
        this.account = model.getAccount();
        this.password = model.getPassword();
        this.role = model.getRole();

        if (model.getUserInfo() != null) {
            this.infoId = model.getInfoId();
            this.userInfo = new UserInfoDto(model.getUserInfo());
        }
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getAccount() {
        return this.account;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public Long getInfoId() {
        return this.infoId;
    }

    @Override
    public UserInfoDto getUserInfo() {
        return this.userInfo;
    }
    
}
