package com.example.persistence.jpa.user;

import com.example.persistence.domain.UserInfoModel;
import com.example.persistence.domain.UserModel;
import com.example.persistence.jpa.user.entity.UserEntity;
import com.example.persistence.jpa.user.entity.UserInfoEntity;

public class ModelDownCasting {
    
    public static UserEntity toUser(UserModel model) {
        if (model == null) return null;
        
        UserEntity downCastingObject = new UserEntity();
        downCastingObject.setUserId(model.getUserId());
        downCastingObject.setAccount(model.getAccount());
        downCastingObject.setPassword(model.getPassword());
        downCastingObject.setRole(model.getRole());
        downCastingObject.setInfoId(model.getInfoId());
        downCastingObject.setUserInfo(toUserInfo(model.getUserInfo()));

        return downCastingObject;
    }

    public static UserInfoEntity toUserInfo(UserInfoModel model) {
        if (model == null) return null;

        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setInfoId(model.getInfoId());
        userInfo.setUserName(model.getUserName());
        userInfo.setBirthDay(model.getBirthDay());
        userInfo.setUserId(model.getUserId());
        userInfo.setUser(toUser(model.getUser()));

        return userInfo;
    }

}
