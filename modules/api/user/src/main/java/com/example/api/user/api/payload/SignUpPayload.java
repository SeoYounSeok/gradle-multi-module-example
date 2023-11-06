package com.example.api.user.api.payload;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.api.user.manage.dto.UserDto;
import com.example.api.user.manage.dto.UserInfoDto;

import lombok.Data;

@Data
public class SignUpPayload {

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    private String userName;

    @NotNull
    private LocalDateTime birthDay;


    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setAccount(account);
        userDto.setPassword(password);

        UserInfoDto infoDto = new UserInfoDto();
        infoDto.setUserName(userName);
        infoDto.setBirthDay(birthDay);

        userDto.setUserInfo(infoDto);

        return userDto;
    }
}
