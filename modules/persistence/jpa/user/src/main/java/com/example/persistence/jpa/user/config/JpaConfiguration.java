package com.example.persistence.jpa.user.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example")
@EntityScan(basePackages = "com.example")
public class JpaConfiguration {
    
}
