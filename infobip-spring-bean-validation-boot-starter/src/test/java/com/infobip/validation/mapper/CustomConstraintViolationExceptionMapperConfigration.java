package com.infobip.validation.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class CustomConstraintViolationExceptionMapperConfigration {

    @Bean
    public CustomValidatedService testService() {
        return mock(CustomValidatedService.class);
    }
}
