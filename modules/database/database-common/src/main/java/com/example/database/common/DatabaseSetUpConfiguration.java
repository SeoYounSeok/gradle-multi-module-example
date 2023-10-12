package com.example.database.common;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseSetUpConfiguration {
    
    private final DriverInfo infoConfig;
    private final DatabaseConfig source;

    @Bean
    public DataSource dataSource() {
        System.out.println("DRIVER SET");
        source.injectDriverInfo(infoConfig);
        return source.getDataSource();
    }

}
