package com.infobip.validation.multiple;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MultipleValidatorsConfiguration {

    @Bean
    Consumer<Integer> integerConsumer() {
        return Mockito.mock(Consumer.class);
    };

    @Bean
    Consumer<Long> longConsumer() {
        return Mockito.mock(Consumer.class);
    };
}
