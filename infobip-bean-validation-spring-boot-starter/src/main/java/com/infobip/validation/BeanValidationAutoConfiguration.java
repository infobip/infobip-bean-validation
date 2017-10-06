package com.infobip.validation;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import com.infobip.validation.api.HibernateValidatorConfigurationStrategy;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
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

    @Autowired(required = false)
    private HibernateValidatorConfigurationStrategy hibernateValidatorConfigurationStrategy;

    @Autowired(required = false)
    private List<? extends ConstraintValidator<?, ?>> validators;

    @ConditionalOnMissingBean(ConstraintViolationExceptionMapper.class)
    @Bean
    public ConstraintViolationExceptionMapper<ConstraintViolationException> defaultConstraintViolationExceptionMapper() {
        return e -> e;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(ConstraintViolationExceptionMapper<?> exceptionMapper) {
        MethodValidationPostProcessor postProcessor = new CustomMethodValidationPostProcessor(exceptionMapper);
        postProcessor.setValidator(localValidatorFactoryBean());
        return postProcessor;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {

        HibernateValidatorConfigurationStrategy strategy = Optional.ofNullable(this.hibernateValidatorConfigurationStrategy)
                                                                   .orElseGet(() -> configuration -> {});
        List<? extends ConstraintValidator> validators = Optional.ofNullable(this.validators)
                                                                 .orElseGet(Collections::emptyList);
        return new CustomLocalValidatorFactoryBean(strategy, validators);
    }
}
