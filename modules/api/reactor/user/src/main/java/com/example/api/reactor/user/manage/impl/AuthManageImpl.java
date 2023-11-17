package com.example.api.reactor.user.manage.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.reactor.user.exceptions.DataDuplicationException;
import com.example.api.reactor.user.exceptions.DataNotFoundException;
import com.example.api.reactor.user.exceptions.PasswordNotMatchException;
import com.example.api.reactor.user.manage.AuthManage;
import com.example.api.reactor.user.manage.dto.UserDto;
import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.utils.encoder.PwEncoder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthManageImpl implements AuthManage {
    
    private final PwEncoder pwEncoder;
    private final UserR2dbcPersistence userRepo;
    
    @Override
    @Transactional
    public Mono<String> signUp(UserDto dto) {
        return existsByAccount(dto.getAccount())
            .flatMap(isExist -> {
                if (isExist) {
                    Exception e = new DataDuplicationException(dto.getAccount());
                    return Mono.defer(() -> Mono.error(e));
                }
                
                String encryptedPw = pwEncoder.encode(dto.getPassword());
                dto.setPassword(encryptedPw);
                dto.setRole("USER");

                return userRepo.save(dto)
                    .flatMap(newUser -> Mono.just(newUser.getUserId()));
            });
    }

    @Override
    @Transactional
    public Mono<String> signIn(UserDto dto) {
        return userRepo.findByAccount(dto.getAccount())
            .flatMap(user -> {
                if (!pwEncoder.match(dto.getPassword(), user.getPassword())) {
                    return Mono.defer(() -> Mono.error(new PasswordNotMatchException()));
                }

                return Mono.just(user.getUserId());
            })
            .switchIfEmpty(Mono.defer(() -> Mono.error(new DataNotFoundException(dto.getAccount()))));
    }
    

    private Mono<Boolean> existsByAccount(String account) {
        return userRepo.findByAccount(account)
            .map(user -> true)
            .switchIfEmpty(Mono.just(false));
    }

}
