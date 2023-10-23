package com.example.persistence.jpa.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.persistence.adapter.common.UserInfoPersistence;
import com.example.persistence.domain.UserInfoModel;
import com.example.persistence.jpa.user.entity.UserInfoEntity;
import com.example.persistence.jpa.user.repository.JpaUserInfoRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserInfoPersistenceImpl implements UserInfoPersistence<UserInfoModel> {
    
    private final JpaUserInfoRepository infoRepository;

    @Override
    public Optional<UserInfoModel> findByInfoId(Long id) {
        return infoRepository.findById(id).map(UserInfoModel.class::cast);
    }

    @Override
    public Optional<UserInfoModel> findByUserName(String name) {
        return infoRepository.findByUserName(name).map(UserInfoModel.class::cast);
    }

    @Override
    public UserInfoModel save(UserInfoModel model) {
        if (model instanceof UserInfoEntity) {
            return infoRepository.save((UserInfoEntity) model);
        }
        return infoRepository.save(ModelDownCasting.toUserInfo(model));
    }

    @Override
    public void remove(Long infoId) {
        infoRepository.deleteById(infoId);
    }
    
}
