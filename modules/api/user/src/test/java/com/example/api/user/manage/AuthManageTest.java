package com.example.api.user.manage;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.api.user.exceptions.DataDuplicationException;
import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.exceptions.PasswordNotMatchException;
import com.example.api.user.manage.dto.UserDto;
import com.example.api.user.manage.impl.AuthManageImpl;
import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;
import com.example.utils.encoder.PwEncoder;

public class AuthManageTest {

    @Mock
    private PwEncoder pwEncoder;

    @Mock
    private UserPersistence userRepo;

    private AuthManage authManage;

    UserDto testDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        authManage = new AuthManageImpl(pwEncoder, userRepo);

        testDto = dtoMocking();
    }

    private UserDto dtoMocking() {
        UserDto dto = new UserDto();
        dto.setUserId("test");
        dto.setAccount("test");
        dto.setPassword("test");
        dto.setRole("user");
        return dto;
    }

    @Test
    void test_signIn_account_notFound() {
        when(userRepo.findByAccount(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            authManage.signIn(testDto);
        });
    }

    @Test
    void test_signIn_password_not_match() {
        testDto.setPassword("not-hashed-pw");

        when(userRepo.findByAccount(any())).thenReturn(Optional.of(((UserModel) testDto)));

        assertThrows(PasswordNotMatchException.class, () -> {
            authManage.signIn(testDto);
        });
    }

    @Test
    void test_signIn_success() {
        when(pwEncoder.match(any(), any())).thenReturn(true);
        when(userRepo.findByAccount(any())).thenReturn(Optional.of(((UserModel) testDto)));

        assertThat(authManage.signIn(testDto)).isEqualTo(testDto.getUserId());
    }

    @Test
    void test_signUp_account_duplicate() {
        when(userRepo.findByAccount(any())).thenReturn(Optional.of(((UserModel) testDto)));

        assertThrows(DataDuplicationException.class, () -> {
            authManage.signUp(testDto);
        });
    }

    @Test
    void test_signUp_success() {
        when(userRepo.findByAccount(testDto.getAccount())).thenReturn(Optional.empty());
        when(pwEncoder.encode(any())).thenReturn("encoded-pw");
        when(userRepo.save(testDto)).thenReturn(testDto);
        
        assertThat(authManage.signUp(testDto)).isEqualTo(testDto.getUserId());
    }

}
