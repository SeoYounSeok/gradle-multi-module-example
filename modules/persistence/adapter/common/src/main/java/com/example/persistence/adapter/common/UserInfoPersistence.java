package com.example.persistence.adapter.common;

import java.util.Optional;

import com.example.persistence.domain.UserInfoModel;

public interface UserInfoPersistence<T extends UserInfoModel> {

    Optional<T> findByInfoId(Long id);

    Optional<T> findByUserName(String name);

    T save(T model);
    
    void remove(Long infoId);
    
}
