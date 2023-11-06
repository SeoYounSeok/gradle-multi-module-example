package com.example.database.jdbc.h2;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.database.interfaces.DriverInfo;

public class TotalH2ConfigTest {

    private DriverInfo validInfo;

    DriverInfo mockInfo;

    @BeforeEach
    void setUp() {
        mockInfo = mock(DriverInfo.class);

        validInfo = new H2DriverInfo(
            "jdbc:h2:mem:testdb",
            "test",
            "test",
            "testDb"
        );
    }

    @Test
    void test_driver_info_vaild_check() {
        assertNotNull(validInfo.url());
        assertNotNull(validInfo.userName());
        assertNotNull(validInfo.password());
    }

    @Test
    void test_connection_with_url_잘못된정보() {
        when(mockInfo.url()).thenReturn("inVaild-url");

        assertThatThrownBy(() -> {
            new H2DatabaseConfig().getDataSource(mockInfo).getConnection();
        })
                .isInstanceOf(SQLException.class)
                .hasMessageStartingWith("No suitable driver found for");
    }

    @Test
    void test_connection_with_driverInfo_유효한정보() {

        assertDoesNotThrow(() -> {
            new H2DatabaseConfig().getDataSource(validInfo).getConnection();
        });
    }

}
