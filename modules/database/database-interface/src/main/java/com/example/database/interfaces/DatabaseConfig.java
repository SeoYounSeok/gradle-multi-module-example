package com.example.database.interfaces;

import javax.sql.DataSource;

public interface DatabaseConfig {

    DataSource getDataSource();

    void injectDriverInfo(DriverInfo config);
    
}
