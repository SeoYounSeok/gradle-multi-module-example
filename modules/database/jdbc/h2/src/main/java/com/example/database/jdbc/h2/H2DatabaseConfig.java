package com.example.database.jdbc.h2;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class H2DatabaseConfig implements DatabaseConfig {

    @Override
    public DataSource getDataSource(DriverInfo info) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl(info.url());
        dataSource.setUsername(info.userName());
        dataSource.setPassword(info.password());
        
        return dataSource;
    }

}
