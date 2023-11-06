package com.example.api.user.api.payload;

import java.time.LocalDateTime;

import com.example.api.user.manage.dto.UserDto;
import com.example.api.user.manage.dto.UserInfoDto;

import lombok.Data;

@Data
public class UpdateUserPayload {
    
    private String password;
    
    private String username;

    private LocalDateTime birthDay;

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setPassword(password);

        UserInfoDto infoDto = new UserInfoDto();
        infoDto.setUserName(username);
        infoDto.setBirthDay(birthDay);

        userDto.setUserInfo(infoDto);

        return userDto;
    }

}
