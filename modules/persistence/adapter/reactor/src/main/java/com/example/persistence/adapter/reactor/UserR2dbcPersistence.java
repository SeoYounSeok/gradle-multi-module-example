package com.example.persistence.adapter.reactor;

import com.example.persistence.domain.UserModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserR2dbcPersistence {

    Mono<UserModel> findByAccount(String account);
    
    Mono<UserModel> findByUserId(String userId);

    Flux<UserModel> searchLikeByAccount(String account);
    
    Mono<UserModel> save(UserModel domain);

    Mono<Void> remove(String userId);

}
