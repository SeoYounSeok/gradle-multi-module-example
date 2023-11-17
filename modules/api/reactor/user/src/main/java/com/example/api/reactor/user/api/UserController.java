package com.example.api.reactor.user.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.api.reactor.user.api.payload.UpdateUserPayload;
import com.example.api.reactor.user.api.results.UserResult;
import com.example.api.reactor.user.manage.UserManage;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserManage userManage;

    @GetMapping("/v1/users/{userId}")
    public ResponseEntity<Mono<UserResult>> fetchUser(@PathVariable @Size(min = 32, max = 36) String userId) {
        Mono<UserResult> user = userManage.getUser(userId)
            .map(dto -> new UserResult(dto));

        return ResponseEntity.ok(user);
    }

    @GetMapping("/v1/users/account/{account}")
    public ResponseEntity<Mono<UserResult>> fetchUserByAccount(@PathVariable @NotBlank String account) {
        Mono<UserResult> user = userManage.getUserByAccount(account)
            .map(dto -> new UserResult(dto));

        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/v1/users/account")
    public ResponseEntity<Flux<UserResult>> fetchUsersByQuery(@RequestParam(name = "query", required = true) @NotBlank String query) {
        Flux<UserResult> users = userManage.searchUser(query)
            .map(dto -> new UserResult(dto));

        return ResponseEntity.ok(users);
    }

    @PutMapping("/v1/users/{userId}")
    public ResponseEntity<Mono<UserResult>> updateUserInfo(@PathVariable @Size(min = 32, max = 36) String userId, @RequestBody UpdateUserPayload payload) {
        Mono<UserResult> updated = userManage.update(userId, payload.toDto())
            .map(dto -> new UserResult(dto));

        return ResponseEntity.ok(updated);
    }

}
