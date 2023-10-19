package com.example.persistence.jpa.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.Assert;

import com.example.persistence.domain.UserInfoModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "info_id")
    @Override
    public Long getInfoId() {
        return this.infoId;
    }

    @Column(name = "username")
    @Override
    public String getUserName() {
        return this.userName;
    }

    @Column(name = "birthday")
    @Override
    public LocalDateTime getBirthDay() {
        return this.birthDay;
    }

    // 이 프로젝트에서는 UserEntity 쪽에서만 FK 갖고 있을 수 있게 영속성 비활성화 설정
    @Transient
    @Override
    public String getUserId() {
        return this.userId;
    }

    // 이 프로젝트에서는 UserEntity 쪽에서만 FK 갖고 있을 수 있게 영속성 비활성화 설정
    @Transient
    @Override
    public UserEntity getUser() {
        return this.user;
    }
    
}
