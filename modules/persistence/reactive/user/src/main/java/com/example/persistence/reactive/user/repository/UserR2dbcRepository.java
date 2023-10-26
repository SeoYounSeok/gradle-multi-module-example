package com.example.persistence.reactive.user.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.persistence.reactive.user.entity.UserEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, String> {

    @Query("SELECT " +
            "       u.user_id as user_id, " +
            "       u.account as account, " +
            "       u.password as password, " +
            "       u.role as role, " +
            "       u.info_id as info_id, " +
            "       i.info_id as info_infoId, " + // info_id 컬럼 매핑시 안겹치게 네이밍 변경
            "       i.username as username, " +
            "       i.birthday as birthday, " +
            "   FROM TEST_USER u " +
            "   LEFT JOIN TEST_USER_INFO i " +
            "   ON u.info_id = i.info_id " +
            "   WHERE u.user_id = :userId " +
            "")
    Mono<UserEntity> findByUserId(String userId);

    Mono<UserEntity> findByAccount(String account);

    Flux<UserEntity> findByAccountContaining(String account);

}
