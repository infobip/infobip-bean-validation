package com.infobip.bean.validation.spring.boot.starter.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class CustomConstraintViolationExceptionMapperConfiguration {

    @Bean
    public CustomValidatedService testService() {
        return mock(CustomValidatedService.class);
    }
}
