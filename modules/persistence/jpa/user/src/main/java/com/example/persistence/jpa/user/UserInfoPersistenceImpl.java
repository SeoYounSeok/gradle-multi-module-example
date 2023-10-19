package com.example.persistence.jpa.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.persistence.adapter.common.UserInfoPersistence;
import com.example.persistence.jpa.user.entity.UserInfoEntity;
import com.example.persistence.jpa.user.repository.JpaUserInfoRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserInfoPersistenceImpl implements UserInfoPersistence<UserInfoEntity> {
    
    private final JpaUserInfoRepository infoRepository;

    @Override
    public Optional<UserInfoEntity> findByInfoId(Long id) {
        return infoRepository.findById(id);
    }

    @Override
    public Optional<UserInfoEntity> findByUserName(String name) {
        return infoRepository.findByUserName(name);
    }

    @Override
    public UserInfoEntity save(UserInfoEntity model) {
        return infoRepository.save(model);
    }

    @Override
    public void remove(Long infoId) {
        infoRepository.deleteById(infoId);
    }
    
}
