package com.infobip.validation;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import com.infobip.validation.api.HibernateValidatorConfigurationStrategy;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolationException;
import java.util.*;

@AutoConfigureBefore(ValidationAutoConfiguration.class)
@ConditionalOnClass(HibernateValidatorConfiguration.class)
@Configuration
public class BeanValidationAutoConfiguration {

    @ConditionalOnMissingBean(ConstraintViolationExceptionMapper.class)
    @Bean
    public ConstraintViolationExceptionMapper<ConstraintViolationException> defaultConstraintViolationExceptionMapper() {
        return e -> e;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(ConstraintViolationExceptionMapper<?> exceptionMapper,
                                                                       ObjectProvider<MethodValidationExcludeFilter> excludeFilters,
                                                                       LocalValidatorFactoryBean localValidatorFactoryBean) {
        MethodValidationPostProcessor postProcessor = new CustomFilteredMethodValidationPostProcessor(excludeFilters.orderedStream(), exceptionMapper);
        postProcessor.setValidator(localValidatorFactoryBean);
        return postProcessor;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(Optional<HibernateValidatorConfigurationStrategy> hibernateValidatorConfigurationStrategy,
                                                               List<? extends ConstraintValidator<?, ?>> validators) {
        HibernateValidatorConfigurationStrategy strategy = hibernateValidatorConfigurationStrategy.orElseGet(
                () -> configuration -> {
                });
        return new CustomLocalValidatorFactoryBean(strategy, validators);
    }
}
