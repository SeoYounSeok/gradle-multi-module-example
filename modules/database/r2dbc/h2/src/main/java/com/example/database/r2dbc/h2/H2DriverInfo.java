package com.example.database.r2dbc.h2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.database.interfaces.DriverInfo;

@Component
public class H2DriverInfo implements DriverInfo {

    private String URL;
    private String USER_NAME;
    private String PASSWORD;
    private String DB_NAME;

    public H2DriverInfo(
            @Value("${database.url}") String url,
            @Value("${database.user-name}") String userName,
            @Value("${database.password}") String password,
            @Value("${database.db}") String dbName) {
        this.URL = url;
        this.USER_NAME = userName;
        this.PASSWORD = password;
        this.DB_NAME = dbName;
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

    @Override
    public String dbName() {
        return DB_NAME;
    }

}

