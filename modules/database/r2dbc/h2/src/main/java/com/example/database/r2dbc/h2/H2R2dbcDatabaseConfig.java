package com.example.database.r2dbc.h2;

import org.springframework.context.annotation.Configuration;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
public class H2R2dbcDatabaseConfig implements DatabaseConfig {

    @Override
    public ConnectionFactory getDataSource(DriverInfo config) {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
        .option(ConnectionFactoryOptions.DRIVER, "h2")
        .option(ConnectionFactoryOptions.PROTOCOL, "mem") // file, mem
        .option(ConnectionFactoryOptions.HOST, config.url())
        .option(ConnectionFactoryOptions.USER, config.userName())
        .option(ConnectionFactoryOptions.PASSWORD, config.password())
        .option(ConnectionFactoryOptions.DATABASE, config.dbName())
        .build();

        return ConnectionFactories.get(options);
    }
    
}
