package com.example.api.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.api.user.api.results.UserResult;
import com.example.api.user.manage.UserManage;
import com.example.api.user.manage.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserManage userManage;


    @GetMapping("/v1/users/{userId}")
    public ResponseEntity<UserResult> getUser(String userId) {
        UserDto userDto = userManage.getUser(userId);

        return ResponseEntity.ok(new UserResult(userDto));
    }
    
}
