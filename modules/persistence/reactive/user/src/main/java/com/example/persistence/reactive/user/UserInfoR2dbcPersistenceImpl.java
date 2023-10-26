package com.example.persistence.reactive.user;

import org.springframework.stereotype.Repository;

import com.example.persistence.adapter.reactor.UserInfoR2dbcPersistence;
import com.example.persistence.domain.UserInfoModel;
import com.example.persistence.reactive.user.entity.UserInfoEntity;
import com.example.persistence.reactive.user.repository.UserInfoR2dbcRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserInfoR2dbcPersistenceImpl implements UserInfoR2dbcPersistence<UserInfoModel> {

    private final UserInfoR2dbcRepository infoRepository;
    
    @Override
    public Mono<UserInfoModel> findByInfoId(Long id) {
        return infoRepository.findById(id).cast(UserInfoModel.class);
    }

    @Override
    public Mono<UserInfoModel> findByUserName(String name) {
        return infoRepository.findByUserName(name).cast(UserInfoModel.class);
    }

    @Override
    public Mono<UserInfoModel> save(UserInfoModel model) {
        if (model instanceof UserInfoEntity) {
            return infoRepository.save((UserInfoEntity) model).cast(UserInfoModel.class);
        }
        return infoRepository.save(ModelDownCasting.toUserInfo(model)).cast(UserInfoModel.class);
    }

    @Override
    public Mono<Void> remove(Long infoId) {
        return infoRepository.deleteById(infoId);
    }
    
}
