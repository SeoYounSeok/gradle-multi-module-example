package com.example.api.user.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.api.user.api.payload.SignInPayload;
import com.example.api.user.api.payload.SignUpPayload;
import com.example.api.user.manage.AuthManage;

import lombok.RequiredArgsConstructor;

@Validated
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthManage userManage;
    
    @PostMapping("/v1/auth/sign-up")
    public ResponseEntity<String> createUser(@Valid @RequestBody SignUpPayload payload) {
        String newUserId = userManage.signUp(payload.toDto());

        return new ResponseEntity<String>(newUserId, HttpStatus.CREATED);
    }

    @PostMapping("/v1/auth/sign-in")
    public ResponseEntity<String> signIn(@Valid @RequestBody SignInPayload payload) {
        String userId = userManage.signIn(payload.toDto());

        return new ResponseEntity<String>(userId, HttpStatus.OK);
    }

}
