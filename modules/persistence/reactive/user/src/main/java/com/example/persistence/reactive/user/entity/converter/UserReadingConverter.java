package com.example.persistence.reactive.user.entity.converter;

import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import com.example.persistence.reactive.user.entity.UserEntity;
import com.example.persistence.reactive.user.entity.UserInfoEntity;

import io.r2dbc.spi.Row;

@ReadingConverter
public class UserReadingConverter implements Converter<Row, UserEntity> {

    @Override
    @Nullable
    public UserEntity convert(Row source) {
        UserInfoEntity info = null;
        if (source.get("info_id", Long.class) != null) {
            info = new UserInfoEntity();
            info.setInfoId(source.get("info_infoId", Long.class));
            info.setUserName(source.get("username", String.class));
            info.setBirthDay(source.get("birthday", LocalDateTime.class));
        }

        return new UserEntity(
            source.get("user_id", String.class), 
            source.get("account", String.class),
            source.get("password", String.class),
            source.get("role", String.class),
            source.get("info_id", Long.class), 
            info
        );
    }
    
}
