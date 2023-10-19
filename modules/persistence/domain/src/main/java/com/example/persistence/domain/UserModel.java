package com.example.persistence.domain;

public interface UserModel {
    String getUserId();
    String getAccount();
    String getPassword();
    String getRole();

    // for Relationship
    Long getInfoId(); // For @Entity
    UserInfoModel getUserInfo(); // For @OneToOne
}