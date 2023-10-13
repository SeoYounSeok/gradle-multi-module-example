package com.example.database.jdbc.h2;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.database.interfaces.DatabaseConfig;
import com.example.database.interfaces.DriverInfo;

public class TotalH2ConfigTest {

    private DriverInfo realInfo;

    DriverInfo mockInfo;

    @BeforeEach
    void setUp() {
        mockInfo = mock(DriverInfo.class);

        realInfo = new H2DriverInfo();
    }

    @Test
    void test_driver_info_vaild_check() {
        assertNotNull(realInfo.driverClassName());
        assertNotNull(realInfo.url());
        assertNotNull(realInfo.userName());
        assertNotNull(realInfo.password());
    }

    @ParameterizedTest(name = "DriverInfo 에 들어있는 값이 h2가 맞는지 확인")
    @CsvSource({ "org.h2.Driver" })
    void test_drivder_className_is_h2(String h2Driver) {
        assertThat(realInfo.driverClassName()).isEqualTo(h2Driver);
    }

    @Test
    void test_connection_with_driverClass_잘못된정보() {
        DatabaseConfig config = new H2DatabaseConfig();
        config.injectDriverInfo(mockInfo);

        when(mockInfo.driverClassName()).thenReturn("inVaild-url");

        assertThatThrownBy(() -> {
            config.getDataSource().getConnection();
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Could not load JDBC driver class");
    }

    @Test
    void test_connection_with_url_잘못된정보() {
        DatabaseConfig config = new H2DatabaseConfig();
        config.injectDriverInfo(mockInfo);

        when(mockInfo.driverClassName()).thenReturn(realInfo.driverClassName());
        when(mockInfo.url()).thenReturn("inVaild-url");

        assertThatThrownBy(() -> {
            config.getDataSource().getConnection();
        })
                .isInstanceOf(SQLException.class)
                .hasMessageStartingWith("No suitable driver found for");
    }

    @Test
    void test_connection_with_driverInfo_유효한정보() {
        DatabaseConfig config = new H2DatabaseConfig();
        config.injectDriverInfo(mockInfo);

        when(mockInfo.driverClassName()).thenReturn(realInfo.driverClassName());
        when(mockInfo.url()).thenReturn(realInfo.url());
        when(mockInfo.userName()).thenReturn(realInfo.userName());
        when(mockInfo.password()).thenReturn(realInfo.password());

        assertDoesNotThrow(() -> {
            config.getDataSource().getConnection();
        });
    }

}
