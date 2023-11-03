package com.example.api.user.manage.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.api.user.exceptions.DataDuplicationException;
import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.exceptions.PasswordNotMatchException;
import com.example.api.user.manage.UserManage;
import com.example.api.user.manage.dto.UserDto;
import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;
import com.example.utils.encoder.PwEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManageImpl implements UserManage {
    
    private final PwEncoder pwEncoder;
    private final UserPersistence userPersistence;

    @Override
    public List<UserDto> searchUser(String query) {
        return userPersistence.searchLikeByAccount(query).stream()
            .map(u -> new UserDto(u))
            .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(String userId) {
        UserModel user = userPersistence.findByUserId(userId)
            .orElseThrow(() -> new DataNotFoundException(userId + " is not found"));

        return new UserDto(user);
    }

    @Override
    public UserDto getUserByAccount(String account) {
        UserModel user = userPersistence.findByAccount(account)
            .orElseThrow(() -> new DataNotFoundException(account + " is not found"));

        return new UserDto(user);
    }

    @Override
    public String signUp(UserDto dto) {
        if (userPersistence.findByAccount(dto.getAccount()).isPresent()) {
            throw new DataDuplicationException(dto.getAccount() + " is duplicated");
        }

        // 비밀번호 암호화
        dto.setPassword(pwEncoder.encode(dto.getPassword()));

        return userPersistence.save(dto).getUserId();
    }

    @Override
    public String signIn(UserDto dto) {
        UserModel user = userPersistence.findByAccount(dto.getAccount())
            .orElseThrow(() -> new DataNotFoundException(dto.getAccount() + " is not found "));

        if (!pwEncoder.match(dto.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }

        return user.getUserId();
    }

}
