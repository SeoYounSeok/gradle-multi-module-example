package com.example.api.reactor.user.manage;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.reactor.user.exceptions.DataDuplicationException;
import com.example.api.reactor.user.exceptions.DataNotFoundException;
import com.example.api.reactor.user.manage.dto.UserDto;
import com.example.api.reactor.user.manage.impl.AuthManageImpl;
import com.example.api.reactor.user.manage.impl.UserManageImpl;
import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.utils.encoder.PwEncoder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

public class AuthAndUserManageImplTest {

    private PwEncoder pwEncoder;
    private UserR2dbcPersistence userRepo;

    private AuthManage authManage;
    private UserManage userManage;

    UserDto testDto;

    @BeforeEach
    void setup() {
        Hooks.onOperatorDebug();
        pwEncoder = mock(PwEncoder.class);
        userRepo = mock(UserR2dbcPersistence.class);
        authManage = new AuthManageImpl(pwEncoder, userRepo);
        userManage = new UserManageImpl(pwEncoder, userRepo);

        testDto = dtoMocking();
    }

    private UserDto dtoMocking() {
        UserDto dto = new UserDto();
        dto.setUserId("test");
        dto.setAccount("test");
        dto.setPassword("test");
        dto.setRole("user");
        return dto;
    }


    @Test
    @Transactional
    void test_signUp_success() {

        when(userRepo.findByAccount(anyString())).thenReturn(Mono.empty());
        when(pwEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepo.save(any(UserDto.class))).thenReturn(Mono.just(testDto));

        Mono<String> result = authManage.signUp(testDto);

        StepVerifier.create(result)
            .expectNext(testDto.getUserId())
            .verifyComplete();
    }

    @Test
    void test_signUp_duplication_account() {

        when(userRepo.findByAccount(anyString())).thenReturn(Mono.just(testDto));

        Mono<String> result = authManage.signUp(testDto);

        StepVerifier.create(result)
            .expectError(DataDuplicationException.class)
            .verify();
    }

    @Test
    void test_getUser_notFound() {
        when(userRepo.findByUserId(anyString())).thenReturn(Mono.empty());

        Mono<UserDto> emptyUser = userManage.getUser(testDto.getUserId());

        StepVerifier.create(emptyUser)
            .expectError(DataNotFoundException.class)
            .verify();
    }


    @Test
    void contextTest() {
        final Flux<String> result = Flux.deferContextual(contextView ->
                Flux.just("A", "B", "C")
                        .parallel(3)
                            .runOn(Schedulers.newParallel("Thread", 3))
                        .map(value -> {
                            final String transactionId = contextView.getOrDefault("transactionId", "");
                            return Thread.currentThread().getName() + " - " + value + " - " + transactionId;
                        })
        );

        result.contextWrite(Context.of("transactionId", "tx-1"))
                .subscribe(System.out::println);
    }
    
}
