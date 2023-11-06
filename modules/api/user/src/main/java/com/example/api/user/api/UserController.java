package com.example.api.user.api;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.api.user.api.payload.UpdateUserPayload;
import com.example.api.user.api.results.UserResult;
import com.example.api.user.manage.UserManage;
import com.example.api.user.manage.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Validated
@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final UserManage userManage;

    @GetMapping("/v1/users/{userId}")
    public ResponseEntity<UserResult> fetchUser(@PathVariable @Size(min = 32, max = 36) String userId) {
        System.out.println(userId);
        UserDto userDto = userManage.getUser(userId);

        return ResponseEntity.ok(new UserResult(userDto));
    }

    @GetMapping("/v1/users/account/{account}")
    public ResponseEntity<UserResult> fetchUserByAccount(@PathVariable @NotBlank String account) {
        UserDto userDto = userManage.getUserByAccount(account);

        return ResponseEntity.ok(new UserResult(userDto));
    }
    
    @GetMapping("/v1/users/account")
    public ResponseEntity<List<UserResult>> fetchUsersByQuery(@RequestParam(name = "query", required = true) @NotBlank String query) {
        List<UserDto> userDtos = userManage.searchUser(query);

        List<UserResult> results = userDtos.stream()
            .map(dto -> new UserResult(dto))
            .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @PutMapping("/v1/users/{userId}")
    public ResponseEntity<UserResult> updateUserInfo(@PathVariable @Size(min = 32, max = 36) String userId, @RequestBody UpdateUserPayload payload) {
        UserDto updated = userManage.update(userId, payload.toDto());

        return ResponseEntity.ok(new UserResult(updated));
    }

}
