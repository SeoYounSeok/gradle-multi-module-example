package com.example.api.reactor.user.manage;

import com.example.api.reactor.user.manage.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserManage {

    Flux<UserDto> searchUser(String query);

    Mono<UserDto> getUser(String userId);

    Mono<UserDto> getUserByAccount(String account);

    Mono<UserDto> update(String userId, UserDto dto);
    
}
