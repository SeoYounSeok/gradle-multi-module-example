package com.example.persistence.jpa.user.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserInfoEntityTest {
    
    @ParameterizedTest(name = "UserEntity 필수 요소 없이 생성 테스트")
    @CsvSource(value = { 
            "null, name Must be Non-Empty",
            ", name Must be Non-Empty",
        },
        nullValues = {"null"}
    )
    void test_UserInfoEntity_constructor(String name, String exMessage) {
        assertThatThrownBy(() -> {
            new UserInfoEntity(name, LocalDateTime.now());
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(exMessage);
    }

    @Test
    @DisplayName("UserInfoEntity 필드값 확인 테스트")
    void test_UserInfoEntity_check_field() {
        UserInfoEntity info = new UserInfoEntity("name-test", LocalDateTime.now());

        assertThat(info.getInfoId()).isNull();
        assertThat(info.getUserName()).isEqualTo("name-test");
        assertThat(info.getBirthDay()).isNotNull();
    }

}
