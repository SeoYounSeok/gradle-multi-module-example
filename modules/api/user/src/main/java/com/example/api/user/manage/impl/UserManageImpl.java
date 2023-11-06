package com.example.api.user.manage.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.manage.UserManage;
import com.example.api.user.manage.dto.UserDto;
import com.example.api.user.manage.dto.UserInfoDto;
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
    public UserDto update(String userId, UserDto dto) {
        UserModel user = userPersistence.findByUserId(userId)
            .orElseThrow(() -> new DataNotFoundException(userId + " is not found "));

        UserDto targetUser = new UserDto(user);
        UserInfoDto targetUserInfo = new UserInfoDto(user.getUserInfo());

        String rawPw = dto.getPassword();
        if (rawPw != null) {
            String encryptedPw = encryptPw(rawPw);
            targetUser.setPassword(encryptedPw);
        }

        String username = dto.getUserInfo().getUserName();
        if (username != null) {
            targetUserInfo.setUserName(username);
        }
        
        LocalDateTime birthDay = dto.getUserInfo().getBirthDay();
        if (birthDay != null) {
            targetUserInfo.setBirthDay(birthDay);
        }

        targetUser.setUserInfo(targetUserInfo);

        UserModel updatedUser = userPersistence.save(targetUser);
         return new UserDto(updatedUser);
    }


    private String encryptPw(String raw) {
        return pwEncoder.encode(raw);
    }

}
