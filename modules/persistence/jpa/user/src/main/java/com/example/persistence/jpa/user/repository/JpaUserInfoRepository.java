package com.example.persistence.jpa.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.persistence.jpa.user.entity.UserInfoEntity;

@Repository
public interface JpaUserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    Optional<UserInfoEntity> findByUserName(String userName);
    // Optional<UserInfoEntity> findByUserId(String userId);
}
