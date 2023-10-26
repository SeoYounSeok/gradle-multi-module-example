package com.example.persistence.reactive.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import com.example.persistence.domain.UserInfoModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Table(name = "TEST_USER_INFO")
public class UserInfoEntity implements UserInfoModel {
    
    private Long infoId;
    private String userName;
    private LocalDateTime birthDay;
    private String userId;
    private UserEntity user;

    public UserInfoEntity(String name, LocalDateTime birthDay) {
        Assert.hasLength(name, "name Must be Non-Empty");
        this.userName = name;
        this.birthDay = birthDay;
    }

    @Id
    @Column("info_id")
    @Override
    public Long getInfoId() {
        return this.infoId;
    }

    @Column("username")
    @Override
    public String getUserName() {
        return this.userName;
    }

    @Column("birthday")
    @Override
    public LocalDateTime getBirthDay() {
        return this.birthDay;
    }

    @Transient
    @Override
    public String getUserId() {
        return this.userId;
    }

    @Transient
    @Override
    public UserEntity getUser() {
        return this.user;
    }

}
