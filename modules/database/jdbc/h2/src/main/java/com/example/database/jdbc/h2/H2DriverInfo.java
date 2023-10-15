package com.example.database.jdbc.h2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.database.interfaces.DriverInfo;

@Component
public class H2DriverInfo implements DriverInfo {

    private String DRIVER_CLASS_NAME;
    private String URL;
    private String USER_NAME;
    private String PASSWORD;

    public H2DriverInfo(
            @Value("${database.driver-class-name}") String driverClassName,
            @Value("${database.url}") String url,
            @Value("${database.user-name}") String userName,
            @Value("${database.password}") String password) {
        this.DRIVER_CLASS_NAME = driverClassName;
        this.URL = url;
        this.USER_NAME = userName;
        this.PASSWORD = password;
    }

    @Override
    public String driverClassName() {
        return DRIVER_CLASS_NAME;
    }

    @Override
    public String url() {
        return URL;
    }

    @Override
    public String userName() {
        return USER_NAME;
    }

    @Override
    public String password() {
        return PASSWORD;
    }

}
