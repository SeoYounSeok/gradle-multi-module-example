package com.example.persistence.jpa.user.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserEntityTest {

    @ParameterizedTest(name = "UserEntity 필수 요소 없이 생성 테스트")
    @CsvSource(value = { 
        "null, password, role, account Must be Non-Empty",
        "account, null, role, password Must be Non-Empty",
        "account, password, null, role Must be Non-Empty",
        },
        nullValues = {"null"}
    )
    void test_UserEntity_생성실패(String account, String password, String role, String exMessage) {
        assertThatThrownBy(() -> {
            new UserEntity(account, password, role);
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(exMessage);
    }

    @Test
    @DisplayName("UserEntity 초기 생성시 userId 값 없는지 테스트")
    void test_UserEntity_userId_null_check() {
        UserEntity newUser = new UserEntity("account", "password", "role");

        assertNull(newUser.getUserId());
    }

    @ParameterizedTest(name = "UserEntity 요소 수정 테스트")
    @CsvSource({
        "account1, password1, role1",
        "account2, password2, role2",
        "account3, password3, role3",
    })
    void test_UserEntity_change_field_Value(String account, String password, String role) {
        UserEntity newUser = new UserEntity("test-account", "test-password", "test-role");

        newUser.setAccount(account);
        newUser.setPassword(password);
        newUser.setRole(role);

        assertThat(newUser.getAccount()).isEqualTo(account);
        assertThat(newUser.getPassword()).isEqualTo(password);
        assertThat(newUser.getRole()).isEqualTo(role);
    }

}
