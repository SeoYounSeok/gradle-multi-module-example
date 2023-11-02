package com.example.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserInfomationController {

    @GetMapping("/test")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok("get method test");
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello";
    }

}
