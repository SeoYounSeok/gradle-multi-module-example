package com.example.database.jdbc.h2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.database.interfaces.DriverInfo;

@Component
public class H2DriverInfo implements DriverInfo {

    @Value("${database.driver-class-name}")
    private String DRIVER_CLASS_NAME;

    @Value("${database.url}")
    private String URL;

    @Value("${database.user-name}")
    private String USER_NAME;

    @Value("${database.password}")
    private String PASSWORD;

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
