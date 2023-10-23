package com.example.persistence.reactive.user.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.persistence.reactive.user.entity.UserInfoEntity;

@Repository
public interface UserInfoR2dbcRepository extends R2dbcRepository<UserInfoEntity, Long> {
    
}
