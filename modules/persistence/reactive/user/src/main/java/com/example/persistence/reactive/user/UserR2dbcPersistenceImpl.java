package com.example.persistence.reactive.user;

import org.springframework.stereotype.Repository;

import com.example.persistence.adapter.reactor.UserR2dbcPersistence;
import com.example.persistence.domain.UserModel;
import com.example.persistence.reactive.user.entity.UserEntity;
import com.example.persistence.reactive.user.repository.UserInfoR2dbcRepository;
import com.example.persistence.reactive.user.repository.UserR2dbcRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserR2dbcPersistenceImpl implements UserR2dbcPersistence {

    private final UserR2dbcRepository userRepository;
    private final UserInfoR2dbcRepository infoRepository;

    @Override
    public Mono<UserModel> findByAccount(String account) {
        return userRepository.findByAccount(account).cast(UserModel.class);
    }

    @Override
    public Mono<UserModel> findByUserId(String userId) {
        return userRepository.findByUserId(userId).cast(UserModel.class);
    }

    @Override
    public Flux<UserModel> searchLikeByAccount(String account) {
        return userRepository.findByAccountContaining(account)
                .map(data -> UserModel.class.cast(data));
    }

    @Override
    public Mono<Void> remove(String userId) {
        return userRepository.deleteById(userId);
    }

    @Override
    public Mono<UserModel> save(UserModel domain) {
        if (domain instanceof UserEntity) {
            return persistWithJoin((UserEntity) domain).cast(UserModel.class);
        }
        return persistWithJoin(ModelDownCasting.toUser(domain)).cast(UserModel.class);
    }

    private Mono<UserEntity> persistWithJoin(UserEntity entity) {
        if (entity.getUserInfo() != null) {
            return infoRepository.save(entity.getUserInfo())
                    .flatMap(newInfo -> {
                        entity.setInfoId(newInfo.getInfoId());
                        return userRepository.save(entity);
                    });
        }
        return userRepository.save(entity);
    }

}
