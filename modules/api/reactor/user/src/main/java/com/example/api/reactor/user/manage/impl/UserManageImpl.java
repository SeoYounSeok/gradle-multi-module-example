package com.example.api.reactor.user.manage.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.api.reactor.user.exceptions.DataNotFoundException;
import com.example.api.reactor.user.manage.UserManage;
import com.example.api.reactor.user.manage.dto.UserDto;
import com.example.api.reactor.user.manage.dto.UserInfoDto;
import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.utils.encoder.PwEncoder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserManageImpl implements UserManage {

    private final PwEncoder pwEncoder;
    private final UserR2dbcPersistence userRepo;

    @Override
    public Flux<UserDto> searchUser(String query) {
        return userRepo.searchLikeByAccount(query)
            .map(model -> new UserDto(model));
    }

    @Override
    public Mono<UserDto> getUser(String userId) {
        return userRepo.findByUserId(userId)
            .flatMap(model -> Mono.just(new UserDto(model)))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(userId))));
    }

    @Override
    public Mono<UserDto> getUserByAccount(String account) {
        return userRepo.findByAccount(account)
            .flatMap(model -> Mono.just(new UserDto(model)))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(account))));
    }

    @Override
    public Mono<UserDto> update(String userId, UserDto dto) {
        Mono<UserDto> userMono = getUser(userId);

        return userMono.flatMap(user -> {

            String rawPw = dto.getPassword();
            if (rawPw != null) {
                String encryptedPw = pwEncoder.encode(rawPw);
                user.setPassword(encryptedPw);
            }

            UserInfoDto userInfo = dto.getUserInfo();

            String username = dto.getUserInfo().getUserName();
            if (username != null) {
                userInfo.setUserName(username);
            }

            LocalDateTime birthDay = dto.getUserInfo().getBirthDay();
            if (birthDay != null) {
                userInfo.setBirthDay(birthDay);
            }

            user.setUserInfo(userInfo);
            
            return userRepo.save(user).map(updated -> new UserDto(updated));
        });
    }
    
}
