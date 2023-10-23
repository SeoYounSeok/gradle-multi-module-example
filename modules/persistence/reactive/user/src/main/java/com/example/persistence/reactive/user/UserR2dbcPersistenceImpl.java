package com.example.persistence.reactive.user;

import org.springframework.stereotype.Repository;

import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.persistence.domain.UserModel;
import com.example.persistence.reactive.user.entity.UserEntity;
import com.example.persistence.reactive.user.repository.UserR2dbcRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserR2dbcPersistenceImpl implements UserR2dbcPersistence<UserModel> {
    
    private final UserR2dbcRepository userRepository;


    @Override
    public Mono<UserModel> findByAccount(String account) {
        return userRepository.findByAccount(account).cast(UserModel.class);
    }

    @Override
    public Mono<UserModel> findByUserId(String userId) {
        return userRepository.findById(userId).cast(UserModel.class);
    }

    @Override
    public Flux<UserModel> searchLikeByAccount(String account) {
        return userRepository.findByAccountContaining(account)
            .map(data -> UserModel.class.cast(data));
    }

    @Override
    public Mono<UserModel> save(UserModel domain) {
        if (domain instanceof UserEntity) {
            return userRepository.save((UserEntity) domain).cast(UserModel.class);
        }
        return userRepository.save(ModelDownCasting.toUser(domain)).cast(UserModel.class);
    }

    @Override
    public Mono<Void> remove(String userId) {
        return userRepository.deleteById(userId);
    }
    
}
