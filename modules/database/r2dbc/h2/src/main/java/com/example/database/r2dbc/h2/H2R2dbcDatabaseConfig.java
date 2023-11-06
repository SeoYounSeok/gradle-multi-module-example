package com.example.database.r2dbc.h2;

import org.springframework.context.annotation.Configuration;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class H2R2dbcDatabaseConfig implements DatabaseConfig {

    @Override
    public ConnectionFactory getDataSource(DriverInfo config) {
        return H2ConnectionFactory
            .inMemory(config.dbName(), config.userName(), config.password());
    }
    
}
