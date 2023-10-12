package com.example.database.jdbc.h2;

import org.springframework.stereotype.Component;

import com.example.database.interfaces.DriverInfo;

@Component
public class H2DriverInfo implements DriverInfo {

    private final String DRIVER_CLASS_NAME = "org.h2.Driver";
    private final String URL = "jdbc:h2:mem:testdb";
    private final String USER_NAME = "test";
    private final String PASSWORD = "test";

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
