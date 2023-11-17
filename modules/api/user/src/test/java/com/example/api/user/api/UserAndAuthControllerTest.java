package com.example.api.user.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.api.user.api.handlers.ArgumentVaildExceptionHandler;
import com.example.api.user.api.handlers.CustomExceptionHandler;
import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.manage.AuthManage;
import com.example.api.user.manage.UserManage;
import com.example.api.user.manage.dto.UserDto;

import net.minidev.json.JSONObject;

@WebMvcTest(
    controllers = {
        UserController.class,
        AuthController.class,
        ArgumentVaildExceptionHandler.class, 
        CustomExceptionHandler.class
    }
)
public class UserAndAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean UserManage userManage;
    @MockBean AuthManage authManage;

    UserDto testDto;
    private UserDto dtoMocking() {
        UserDto dto = new UserDto();
        dto.setUserId("b2dcc9b7-8e2d-4cd5-ad67-01dfc9bcb3de"); // 32길이 이상 ID 필요
        dto.setAccount("test");
        dto.setPassword("test");
        dto.setRole("user");
        return dto;
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        testDto = dtoMocking();
    }



    @Test
    @DisplayName("/v1/users/{userId} 파라미터 유효성 검사 테스트")
    void test_fetchUser_invaild_pathParam() throws Exception {
        mockMvc.perform(get("/v1/users/{userId}", "???"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400 BAD_REQUEST"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("/v1/users/{userId} 유저 없을때 테스트")
    void test_fetchUser_notFound() throws Exception {
        when(userManage.getUser(testDto.getUserId())).thenThrow(new DataNotFoundException(""));

        mockMvc.perform(get("/v1/users/{userId}", testDto.getUserId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("/v1/users/{userId} 정상 조회 테스트")
    void test_fetchUser() throws Exception {
        when(userManage.getUser(testDto.getUserId())).thenReturn(testDto);

        mockMvc.perform(get("/v1/users/{userId}", testDto.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(testDto.getUserId()))
                .andExpect(jsonPath("$.account").value(testDto.getAccount()));
    }

    @Test
    @DisplayName("/v1/users/account/{account} 쿼리 파라미터 유효성 검사 테스트")
    void test_fetchUser_by_account_param_invaild() throws Exception {
        mockMvc.perform(get("/v1/users/account/{account}", " "))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").exists());
    }


    @Test
    @DisplayName("/v1/users/account/{account} 유저 없을때 테스트")
    void test_fetchUser_by_account_notFound() throws Exception {
        when(userManage.getUserByAccount(testDto.getAccount())).thenThrow(new DataNotFoundException(""));

        mockMvc.perform(get("/v1/users/account/{account}", testDto.getAccount()))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("/v1/users/account/{account} 정상 조회 테스트")
    void test_fetchUser_by_account() throws Exception {
        when(userManage.getUserByAccount(testDto.getAccount())).thenReturn(testDto);

        mockMvc.perform(get("/v1/users/account/{account}", testDto.getAccount()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(testDto.getUserId()))
            .andExpect(jsonPath("$.account").value(testDto.getAccount()));  
    }



    @ParameterizedTest(name = "/v1/auth/sign-up payload 유효성 검사 테스트")
    @CsvSource(value = { 
        "test-account, test-password, test-name, null", 
        "test-account, test-password, null, 2021-08-01T16:26:39.098", 
        "test-account, null, test-name, 2021-08-01T16:26:39.098", 
        "null, test-password, test-name, 2021-08-01T16:26:39.098", 
    }, nullValues = { "null" })
    void test_signUp_payload_invaild(String account, String password, String userName, LocalDateTime birthDay) throws Exception {

        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("password", password);
        requestBody.put("userName", userName);
        requestBody.put("birthDay", birthDay);

        mockMvc.perform(
            post("/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(requestBody.toJSONString())
        )
        .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("/v1/auth/sign-up 정상 등록 테스트")
    void test_signUp() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", testDto.getAccount());
        requestBody.put("password", testDto.getPassword());
        requestBody.put("userName", "userName");
        requestBody.put("birthDay", "2021-08-01T16:26:39.098");

        when(authManage.signUp(any())).thenReturn(testDto.getUserId());

        mockMvc.perform(
            post("/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(requestBody.toJSONString())
        )
        .andExpect(status().isCreated());
    }

    @ParameterizedTest(name = "/v1/auth/sign-in payload 유효성 검사 테스트")
    @CsvSource(value = {"test-account, null", "null, test-password"}, nullValues = { "null" })
    void test_signIn_payload_invaild(String account, String password) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", account);
        requestBody.put("password", password);

        mockMvc.perform(
            post("/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(requestBody.toJSONString())
        )
        .andExpect(status().isBadRequest());        
    }

    @Test
    @DisplayName("/v1/auth/sign-in 정상 등록 테스트")
    void test_signIn() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("account", testDto.getAccount());
        requestBody.put("password", testDto.getPassword());
        
        when(authManage.signIn(any())).thenReturn(testDto.getUserId());

        mockMvc.perform(
            post("/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)  
                .content(requestBody.toJSONString())
        )
        .andExpect(status().isOk());        
    }
}
