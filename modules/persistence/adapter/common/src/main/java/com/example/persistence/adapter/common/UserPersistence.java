package com.example.persistence.adapter.common;

import java.util.List;
import java.util.Optional;

import com.example.persistence.domain.UserModel;

public interface UserPersistence {

    Optional<UserModel> findByUserId(String userId);

    Optional<UserModel> findByAccount(String account);

    List<UserModel> searchLikeByAccount(String account);
    
    UserModel save(UserModel domain);

    void remove(String userId);

}
