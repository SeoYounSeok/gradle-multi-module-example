package com.example.api.user.manage;

import java.util.List;

import com.example.api.user.manage.dto.UserDto;

public interface UserManage {
    
    List<UserDto> searchUser(String query);

    UserDto getUser(String userId);

    UserDto getUserByAccount(String account);

    UserDto update(String userId, UserDto dto);

}
