package com.example.utils.encoder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.example.utils.encoder.DefaultPwEncoder;
import com.example.utils.encoder.PwEncoder;

@Configuration
@Conditional(PwEncoderCondition.class)
public class EncoderConfiguration {
    
    @Bean
    public PwEncoder defaultPwEncoder() {
        return new DefaultPwEncoder();
    }
    
}
