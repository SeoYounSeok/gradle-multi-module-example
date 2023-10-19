package com.example.persistence.jpa.user.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import com.example.persistence.jpa.user.config.JpaConfiguration;
import com.example.persistence.jpa.user.entity.UserEntity;
import com.example.utils.generator.UniqueIdGenerator;


@DataJpaTest
@ContextConfiguration(classes = {JpaUserRepository.class, JpaConfiguration.class})
public class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity("test-account", "test-password", "test-role");
    }


    @Test
    void contextLoad() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    @DisplayName("JpaUserRepository 저장 테스트")
    void test_JpaUserRepository_save_test() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            when(UniqueIdGenerator.uuid()).thenReturn("test-id");

            userRepository.save(testUser);

            Optional<UserEntity> newUser = userRepository.findById(testUser.getUserId());
            assertThat(newUser.isPresent()).isTrue();
        }
    }


    @Test
    @DisplayName("JpaUserRepository Id 중복 저장 실패 테스트")
    void test_JpaUserRepository_duplicate_id_save() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            when(UniqueIdGenerator.uuid()).thenReturn("duplicate-id");

            userRepository.save(testUser);
            
            assertThrows(DataIntegrityViolationException.class, () -> {
                userRepository.save(new UserEntity("test", "test", "test"));
            });
        }
    }


    @Test
    @DisplayName("JpaUserRepository findByAccount NOT-FOUND 테스트")
    void test_findByAccount_not_found() {
        Optional<UserEntity> user = userRepository.findByAccount(testUser.getAccount());

        assertFalse(user.isPresent());
    }

    @Test
    @DisplayName("JpaUserRepository findByAccount 테스트")
    void test_findByAccount() {
        userRepository.save(testUser);

        Optional<UserEntity> user = userRepository.findByAccount(testUser.getAccount());

        assertTrue(user.isPresent());
    }


    @ParameterizedTest(name = "findByAccountContaining like 검색 테스트")
    @CsvSource({ "test, t", "test, e", "test, est" })
    void test_findByAccountContaining(String account, String query) {
        userRepository.save(new UserEntity(account, "test", "test"));

        assertThat(userRepository.findByAccountContaining(query))
            .hasSize(1);
    }

    @Test
    @DisplayName("existsByUserId false 테스트")
    void test_existByUserId_false() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            String testId = "test-id";
            when(UniqueIdGenerator.uuid()).thenReturn(testId);

            assertThat(userRepository.existsById(testId)).isFalse();
        }
    }

    @Test
    @DisplayName("existsByUserId true 테스트")
    void test_existByUserId_true() {
        try (MockedStatic<UniqueIdGenerator> mockUtil = mockStatic(UniqueIdGenerator.class)) {
            String testId = "test-id";
            when(UniqueIdGenerator.uuid()).thenReturn(testId);

            userRepository.save(testUser);

            assertThat(userRepository.existsById(testId)).isTrue();
        }
    }

    
}
