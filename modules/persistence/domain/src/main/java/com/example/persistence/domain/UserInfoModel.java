package com.example.persistence.domain;

import java.time.LocalDateTime;

public interface UserInfoModel {
    Long getInfoId();
    String getUserName();
    LocalDateTime getBirthDay();
    
    // for Relationship
    String getUserId(); // For @Entity
    UserModel getUser(); // For @OneToOne
}
