package com.example.api.user.manage.impl;

import org.springframework.stereotype.Service;

import com.example.api.user.exceptions.DataDuplicationException;
import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.exceptions.PasswordNotMatchException;
import com.example.api.user.manage.AuthManage;
import com.example.api.user.manage.dto.UserDto;
import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;
import com.example.utils.encoder.PwEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthManageImpl implements AuthManage {

    private final PwEncoder pwEncoder;
    private final UserPersistence userPersistence;

    @Override
    public String signUp(UserDto dto) {
        if (userPersistence.findByAccount(dto.getAccount()).isPresent()) {
            throw new DataDuplicationException(dto.getAccount() + " is duplicated");
        }

        // 비밀번호 암호화
        String encryptedPw = encryptPw(dto.getPassword());
        dto.setPassword(encryptedPw);

        dto.setRole("USER");

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


    private String encryptPw(String raw) {
        return pwEncoder.encode(raw);
    }
    
}
