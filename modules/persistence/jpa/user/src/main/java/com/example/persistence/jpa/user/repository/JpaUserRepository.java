package com.example.persistence.jpa.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.persistence.jpa.user.entity.UserEntity;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByAccount(String account);
    List<UserEntity> findByAccountContaining(String account);
}
