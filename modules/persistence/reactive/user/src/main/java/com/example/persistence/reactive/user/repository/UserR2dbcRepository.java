package com.example.persistence.reactive.user.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.persistence.reactive.user.entity.UserEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, String> {
    Mono<UserEntity> findByAccount(String account);
    Flux<UserEntity> findByAccountContaining(String account);
}
