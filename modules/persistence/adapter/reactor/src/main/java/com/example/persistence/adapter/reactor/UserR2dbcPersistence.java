package com.example.persistence.adapter.reactor;

import org.reactivestreams.Publisher;

import com.example.persistence.domain.UserModel;

public interface UserR2dbcPersistence {

    Publisher<UserModel> findByAccount(String account);
    
    Publisher<UserModel> findByUserId(String userId);

    Publisher<UserModel> searchLikeByAccount(String account);

    Publisher<Boolean> isExist();
    
    Publisher<UserModel> save(UserModel domain);

    Publisher<Boolean> remove(String userId);


}
