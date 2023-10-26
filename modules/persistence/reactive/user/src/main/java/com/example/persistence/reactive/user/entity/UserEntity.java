package com.example.persistence.reactive.user.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import com.example.persistence.domain.UserModel;
import com.example.persistence.reactive.user.entity.callback.UniqueIdEntity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Table(name = "TEST_USER")
@Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserEntity implements UserModel, UniqueIdEntity {

    private String userId;
    private String account;
    private String password;
    private String role;
    private Long infoId;

    @Transient
    private UserInfoEntity userInfo;

    public UserEntity(String account, String password, String role) {
        Assert.hasLength(account, "account Must be Non-Empty");
        Assert.hasLength(password, "password Must be Non-Empty");
        Assert.hasLength(role, "role Must be Non-Empty");

        this.account = account;
        this.password = password;
        this.role = role;
    }
    
    @Id
    @Column("user_id")
    @Override
    public String getUserId() {
        return this.userId;
    }

    @Column("account")
    @Override
    public String getAccount() {
        return this.account;
    }

    @Column("password")
    @Override
    public String getPassword() {
        return this.password;
    }

    @Column("role")
    @Override
    public String getRole() {
        return this.role;
    }

    @Column("info_id")
    @Override
    public Long getInfoId() {
        return this.infoId;
    }

    @Override
    public UserInfoEntity getUserInfo() {
        return this.userInfo;
    }

    @Override
    public void generateId(String uuid) {
        if (userId == null) {
            userId = uuid;
        }
    }
    
}
