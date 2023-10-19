package com.example.persistence.adapter.common;

import java.util.List;
import java.util.Optional;

import com.example.persistence.domain.UserModel;

public interface UserPersistence<T extends UserModel> {

    Optional<T> findByUserId(String userId);

    Optional<T> findByAccount(String account);

    List<T> searchLikeByAccount(String account);
    
    T save(T domain);

    void remove(String userId);

}
