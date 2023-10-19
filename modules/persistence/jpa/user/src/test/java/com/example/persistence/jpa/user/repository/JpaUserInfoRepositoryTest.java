package com.example.persistence.jpa.user.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.jpa.user.config.JpaConfiguration;
import com.example.persistence.jpa.user.entity.UserInfoEntity;

@DataJpaTest
@ContextConfiguration(classes = {JpaUserInfoRepository.class, JpaConfiguration.class})
public class JpaUserInfoRepositoryTest {
    
    @Autowired
    private JpaUserInfoRepository infoRepository;

    @MockBean
    private JpaUserRepository userRepository;

    private UserInfoEntity testInfo;

    @BeforeEach
    void beforeEach() {
        testInfo = new UserInfoEntity("test", LocalDateTime.now());
    }

    @Test
    void context_load() {
        assertThat(infoRepository).isNotNull();
    }


    @Test
    @DisplayName("JpaUserInfoRepository save 테스트")
    void test_JpaUserInfoRepository_save() {
        UserInfoEntity saved = infoRepository.save(testInfo);

        Optional<UserInfoEntity> newInfo = infoRepository.findById(saved.getInfoId());

        assertThat(newInfo.isPresent()).isTrue();
    }


    @Test
    @DisplayName("JpaUserInfoRepository findBy Not-Found 테스트")
    void test_findByInfoId_not_found() {
        assertFalse(infoRepository.findById(99L).isPresent());
    }


    @ParameterizedTest(name = "findByUserName 테스트")
    @CsvSource({ "hello, hell, false", "test, test, true" })
    void test_findByUserName(String name, String search, boolean result) {
        infoRepository.save(new UserInfoEntity(name, null));
        assertTrue(infoRepository.findByUserName(search).isPresent() == result);
    }

}
