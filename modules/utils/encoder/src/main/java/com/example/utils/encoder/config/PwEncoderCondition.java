package com.example.utils.encoder.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.example.utils.encoder.PwEncoder;

public class PwEncoderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (beanFactory == null) {
            return false;
        }

        boolean isNotExist = beanFactory.getBeansOfType(PwEncoder.class).isEmpty();

        return isNotExist;
    }
    
}