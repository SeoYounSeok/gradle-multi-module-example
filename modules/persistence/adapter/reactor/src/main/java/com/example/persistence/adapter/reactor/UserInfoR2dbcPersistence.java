package com.example.persistence.adapter.reactor;

import com.example.persistence.domain.UserInfoModel;

import reactor.core.publisher.Mono;

public interface UserInfoR2dbcPersistence {
    Mono<UserInfoModel> findByInfoId(Long id);

    Mono<UserInfoModel> findByUserName(String name);

    Mono<UserInfoModel> save(UserInfoModel model);

    Mono<Void> remove(Long infoId);
}
