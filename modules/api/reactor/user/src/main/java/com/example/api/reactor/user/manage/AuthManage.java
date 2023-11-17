package com.example.api.reactor.user.manage;

import com.example.api.reactor.user.manage.dto.UserDto;

import reactor.core.publisher.Mono;

public interface AuthManage {

    Mono<String> signUp(UserDto dto);
    
    Mono<String> signIn(UserDto dto);
}
