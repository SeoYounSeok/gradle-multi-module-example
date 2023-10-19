package com.example.persistence.jpa.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.example.persistence.domain.UserModel;
import com.example.utils.generator.UniqueIdGenerator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TEST_USER")
@Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class UserEntity implements UserModel, Persistable<String> {

    private String userId;
    private String account;
    private String password;
    private String role;

    private Long infoId;
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
    @Column(name = "user_id")
    @Override
    public String getUserId() {
        return this.userId;
    }

    @Column(name = "account")
    @Override
    public String getAccount() {
        return this.account;
    }

    @Column(name = "password")
    @Override
    public String getPassword() {
        return this.password;
    }

    @Column(name = "role")
    @Override
    public String getRole() {
        return this.role;
    }

    @Transient
    @Override
    @Nullable
    public String getId() {
        return this.userId;
    }

    @Transient
    @Override
    public boolean isNew() {
        return this.userId == null;
    }

    @PrePersist
    public void generateId() {
        this.userId = UniqueIdGenerator.uuid();
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id")
    @Override
    public UserInfoEntity getUserInfo() {
        return this.userInfo;
    }

    @Transient
    @Override
    public Long getInfoId() {
        if (userInfo == null) {
            return null;
        }
        return this.userInfo.getInfoId();
    }

}
