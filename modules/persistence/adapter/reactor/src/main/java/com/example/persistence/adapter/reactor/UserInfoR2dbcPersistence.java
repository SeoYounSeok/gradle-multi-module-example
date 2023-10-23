package com.example.persistence.adapter.reactor;

import reactor.core.publisher.Mono;

public interface UserInfoR2dbcPersistence<T> {
    Mono<T> findByInfoId(Long id);

    Mono<T> findByUserName(String name);

    Mono<T> save(T model);

    Mono<Void> remove(Long infoId);
}
