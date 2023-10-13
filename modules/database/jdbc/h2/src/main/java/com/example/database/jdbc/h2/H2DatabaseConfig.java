package com.example.database.jdbc.h2;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

import lombok.RequiredArgsConstructor;

@Qualifier("DatabaseConfig")
@Component
@RequiredArgsConstructor
public class H2DatabaseConfig implements DatabaseConfig {

    private DriverInfo info;
    
    @Override
    public void injectDriverInfo(DriverInfo config) {
        this.info = config;
    }

    @Override
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(info.driverClassName());
        dataSource.setUrl(info.url());
        dataSource.setUsername(info.userName());
        dataSource.setPassword(info.password());
        return dataSource;
    }

}
