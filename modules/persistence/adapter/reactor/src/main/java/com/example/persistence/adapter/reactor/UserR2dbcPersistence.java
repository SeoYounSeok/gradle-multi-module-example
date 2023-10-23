package com.example.persistence.adapter.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserR2dbcPersistence<T> {

    Mono<T> findByAccount(String account);
    
    Mono<T> findByUserId(String userId);

    Flux<T> searchLikeByAccount(String account);
    
    Mono<T> save(T domain);

    Mono<Void> remove(String userId);

}
