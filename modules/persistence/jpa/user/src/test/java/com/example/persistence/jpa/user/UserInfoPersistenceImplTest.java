package com.example.persistence.jpa.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.adapter.common.UserInfoPersistence;
import com.example.persistence.jpa.user.config.JpaConfiguration;
import com.example.persistence.jpa.user.entity.UserEntity;
import com.example.persistence.jpa.user.entity.UserInfoEntity;
import com.example.persistence.jpa.user.repository.JpaUserRepository;

@DataJpaTest
@ContextConfiguration(classes = { UserInfoPersistenceImpl.class, JpaConfiguration.class })
public class UserInfoPersistenceImplTest {

    @Autowired
    private UserInfoPersistence<UserInfoEntity> infoPersistence;

    private UserInfoEntity testInfo;

    @BeforeEach
    void beforeEach() {
        testInfo = new UserInfoEntity("test", LocalDateTime.now());
    }

    @Test
    void context_load() {
        assertThat(infoPersistence).isNotNull();
    }


    @Autowired
    private JpaUserRepository userRepository;

    @Test
    @DisplayName("UserEntity와 연관관계 삭제 테스트")
    void test_remove_with_userEntity() {

        UserEntity user = new UserEntity("test", "test", "test");
        user.setUserInfo(testInfo);

        userRepository.save(user);

        String userId = user.getUserId();

        assertTrue(userRepository.findById(userId).isPresent());
        assertTrue(infoPersistence.findByInfoId(testInfo.getInfoId()).isPresent());

        userRepository.deleteById(userId);

        assertFalse(userRepository.findById(userId).isPresent());
        assertFalse(infoPersistence.findByInfoId(testInfo.getInfoId()).isPresent());
    }

}
