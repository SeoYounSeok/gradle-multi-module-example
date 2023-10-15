package com.example.database.interfaces;

import javax.sql.DataSource;

public interface DatabaseConfig {

    DataSource getDataSource(DriverInfo config);

    // void injectDriverInfo(DriverInfo config);
    
}
