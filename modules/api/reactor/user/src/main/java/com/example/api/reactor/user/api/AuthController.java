package com.example.api.reactor.user.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.api.reactor.user.api.payload.SignInPayload;
import com.example.api.reactor.user.api.payload.SignUpPayload;
import com.example.api.reactor.user.manage.AuthManage;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthManage authManage;
    
    @PostMapping("/v1/auth/sign-up")
    public ResponseEntity<Mono<String>> createUser(@Valid @RequestBody SignUpPayload payload) {
        Mono<String> newUserId = authManage.signUp(payload.toDto());

        return new ResponseEntity<Mono<String>>(newUserId, HttpStatus.CREATED);
    }

    @PostMapping("/v1/auth/sign-in")
    public ResponseEntity<Mono<String>> signIn(@Valid @RequestBody SignInPayload payload) {
        Mono<String> userId = authManage.signIn(payload.toDto());

        return new ResponseEntity<Mono<String>>(userId, HttpStatus.OK);
    }
}
