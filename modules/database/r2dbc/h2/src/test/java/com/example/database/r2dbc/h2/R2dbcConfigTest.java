package com.example.database.r2dbc.h2;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import com.example.database.interfaces.DriverInfo;

import io.r2dbc.spi.Connection;
import reactor.test.StepVerifier;

public class R2dbcConfigTest {
    
    DriverInfo mockInfo = mock(DriverInfo.class);

    Publisher<? extends Connection> conn;

    @Test
    void test_inject_null_exception() {
        assertThatNullPointerException()
            .isThrownBy(() -> new H2R2dbcDatabaseConfig().getDataSource(null))
            .withMessage(null);
    }

    @Test
    void test_connection() {
        when(mockInfo.userName()).thenReturn("test");
        when(mockInfo.password()).thenReturn("test");
        when(mockInfo.dbName()).thenReturn("testDb");

        conn = new H2R2dbcDatabaseConfig().getDataSource(mockInfo).create();

        StepVerifier.create(conn)
            .expectNextCount(1)
            .verifyComplete();
    }

}
