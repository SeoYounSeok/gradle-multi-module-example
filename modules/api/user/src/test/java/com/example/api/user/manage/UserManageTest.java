package com.example.api.user.manage;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.manage.dto.UserDto;
import com.example.api.user.manage.dto.UserInfoDto;
import com.example.api.user.manage.impl.UserManageImpl;
import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;
import com.example.utils.encoder.PwEncoder;


public class UserManageTest {

    @Mock
    private PwEncoder pwEncoder;

    @Mock
    private UserPersistence userRepo;

    private UserManage userManage;

    UserDto testDto;
    

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userManage = new UserManageImpl(pwEncoder, userRepo);

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
    void test_getUser_notFound() {
        when(userRepo.findByUserId(any())).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> {
            userManage.getUser("some-user");
        });
    }

    @Test
    void test_getUser_success() {
        when(userRepo.findByUserId(any())).thenReturn(Optional.of(((UserModel) testDto)));

        UserDto result = userManage.getUser("some-user-id");

        assertThat(result.getUserId()).isEqualTo(testDto.getUserId());
    }

    @Test
    void test_getUserByAccount_notFound() {
        when(userRepo.findByAccount(any())).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> {
            userManage.getUserByAccount("some-account");
        });
    }

    @Test
    void test_getUserByAccount_success() {
        when(userRepo.findByAccount(any())).thenReturn(Optional.of(((UserModel) testDto)));

        UserDto result = userManage.getUserByAccount("some-account");

        assertThat(result.getAccount()).isEqualTo(testDto.getAccount());
    }

    @Test
    void test_searchUser() {
        when(userRepo.searchLikeByAccount(any())).thenReturn(List.of((UserModel) testDto));

        List<UserDto> users = userManage.searchUser("like-query");

        for (var u : users) {
            assertThat(u).isInstanceOf(UserModel.class);
            assertThat(u).isInstanceOf(UserDto.class);
        }

        assertThat(users.size()).isGreaterThan(0);
    }
    

    @Test
    void test_user_update_user_notFound() {
        String notFoundUserId = "test";
        when(userRepo.findByUserId(notFoundUserId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            userManage.update(notFoundUserId, testDto);
        });
    }

    @Test
    void test_user_update_success() {
        UserInfoDto testInfo = new UserInfoDto();
        testInfo.setInfoId(1L);
        testInfo.setUserName("test-name");
        testInfo.setBirthDay(LocalDateTime.of(2020, 1, 1, 0, 0));
        testDto.setUserInfo(testInfo);

        when(userRepo.findByUserId(testDto.getUserId())).thenReturn(Optional.of(((UserModel) testDto)));

        UserDto updatedUser = deepCopyForUser(testDto);
        UserInfoDto updatedInfo = deepCopyForInfo(testInfo);
        updatedInfo.setUserName(testInfo.getUserName() + "2");
        updatedInfo.setBirthDay(testInfo.getBirthDay().plusDays(1));
        updatedUser.setUserInfo(updatedInfo);

        when(userRepo.save(any())).thenReturn(updatedUser);

        UserDto updated = userManage.update(testDto.getUserId(), testDto);

        assertThat(updated).isNotNull();
        assertThat(updated.getUserInfo().getBirthDay()).isNotEqualTo(testInfo.getBirthDay());
        assertThat(updated.getUserInfo().getUserName()).isNotEqualTo(testInfo.getUserName());
    }

    

    private UserDto deepCopyForUser(UserDto dto) {
        UserDto copyDto = new UserDto();
        copyDto.setUserId(dto.getUserId());
        copyDto.setAccount(dto.getAccount());
        copyDto.setPassword(dto.getPassword());
        copyDto.setRole(dto.getRole());
        copyDto.setInfoId(dto.getInfoId());
        copyDto.setUserInfo(dto.getUserInfo());
        return copyDto;
    }

    private UserInfoDto deepCopyForInfo(UserInfoDto dto) {
        UserInfoDto copyDto = new UserInfoDto();
        copyDto.setInfoId(dto.getInfoId());
        copyDto.setUserName(dto.getUserName());
        copyDto.setBirthDay(dto.getBirthDay());
        copyDto.setUserId(dto.getUserId());
        copyDto.setUser(dto.getUser());
        return copyDto;
    }
}
