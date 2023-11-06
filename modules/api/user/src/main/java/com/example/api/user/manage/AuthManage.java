package com.example.api.user.manage;

import com.example.api.user.manage.dto.UserDto;

public interface AuthManage {

    String signUp(UserDto dto);

    String signIn(UserDto dto);

    
}
