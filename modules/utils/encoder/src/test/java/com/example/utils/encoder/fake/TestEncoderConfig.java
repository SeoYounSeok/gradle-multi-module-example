package com.example.utils.encoder.fake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.utils.encoder.PwEncoder;

@Configuration
public class TestEncoderConfig {
    
    @Bean
    public PwEncoder pwEncoderBean() {
        return new TestEncoderBean();
    }
}
