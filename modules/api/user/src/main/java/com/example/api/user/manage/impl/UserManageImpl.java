package com.example.api.user.manage.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.api.user.manage.UserManage;
import com.example.api.user.manage.dto.UserDto;
import com.example.persistence.adapter.common.UserPersistence;
import com.example.persistence.domain.UserModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManageImpl implements UserManage {
    
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
            .orElseThrow(() -> new NoSuchElementException());

        return new UserDto(user);
    }

    @Override
    public UserDto getUserByAccount(String account) {
        UserModel user = userPersistence.findByAccount(account)
            .orElseThrow(() -> new NoSuchElementException());

        return new UserDto(user);
    }


    @Override
    public void signUp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signUp'");
    }

    @Override
    public void signIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signIn'");
    }

    @Override
    public void signOut() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signOut'");
    }




}
