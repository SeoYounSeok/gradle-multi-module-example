package com.example.utils.encoder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.utils.encoder.config.EncoderConfiguration;
import com.example.utils.encoder.fake.TestEncoderConfig;

public class EncoderConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void beforeEach() {
        context = new AnnotationConfigApplicationContext();
    }

    @AfterEach
    void afterEach() {
        context.close();
    }

    @Test
    @DisplayName("PwEncoder 사용자 빈 등록시 디폴트 빈 등록 안됨 테스트")
    public void test_PwEncoder_custom_bean() {
        context.register(TestEncoderConfig.class);
        context.register(EncoderConfiguration.class);
        context.refresh();

        assertThat(context.getBeanNamesForType(PwEncoder.class).length).isEqualTo(1);
        assertThat(context.getBean(PwEncoder.class))
            .isNotExactlyInstanceOf(DefaultPwEncoder.class);
    }


    @Test
    @DisplayName("PwEncoder 추가 빈 등록 없다면 디폴트 빈 로드 테스트")
    public void test_PwEncoder_dafault_bean_load() {
        context.register(EncoderConfiguration.class);
        context.refresh();

        assertThat(context.getBean(PwEncoder.class))
            .isInstanceOf(DefaultPwEncoder.class);
    }
}
