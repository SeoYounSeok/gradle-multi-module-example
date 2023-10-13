package com.example.database.jdbc.h2;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

public class TotalH2ConfigTest {

    private DatabaseConfig config;

    DriverInfo mockInfo;

    @BeforeEach
    void setUp() {
        mockInfo = mock(DriverInfo.class);
        
        config = new H2DatabaseConfig();
        config.injectDriverInfo(mockInfo);
    }
    
    @Test
    void test_connection_with_driverInfo_잘못된정보() {
        when(mockInfo.driverClassName()).thenReturn("inVaild-url");

        assertThatThrownBy(() -> {
            config.getDataSource().getConnection();
        })
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("Could not load JDBC driver class");
    }

    @Test
    void test_connection_with_driverInfo_유효한정보() {
        DriverInfo realInfo = new H2DriverInfo();

        when(mockInfo.driverClassName()).thenReturn(realInfo.driverClassName());
        when(mockInfo.url()).thenReturn(realInfo.url());
        when(mockInfo.userName()).thenReturn(realInfo.userName());
        when(mockInfo.password()).thenReturn(realInfo.password());

        assertDoesNotThrow(() -> {
            config.getDataSource().getConnection();
        });
    }


}
