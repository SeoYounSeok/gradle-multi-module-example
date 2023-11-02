package com.example.api.user.manage;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.api.user.manage.dto.UserDto;
import com.example.api.user.manage.impl.UserManageImpl;
import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;


public class UserManageTest {

    // @Autowired
    // private UserManage userManage;

    @Mock
    private UserPersistence userRepo;

    // @InjectMocks
    private UserManage userManage;
    
    // @Mock
    // private UserPersistence<UserModel> userRepo;



    UserDto testDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userManage = new UserManageImpl(userRepo);

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
    
}
