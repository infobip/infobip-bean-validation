package com.infobip.validation;

import java.util.List;
import java.util.Optional;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import com.infobip.validation.api.HibernateValidatorConfigurationStrategy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolationException;
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
        var postProcessor = new CustomFilteredMethodValidationPostProcessor(excludeFilters.orderedStream(), exceptionMapper);
        postProcessor.setValidator(localValidatorFactoryBean);
        return postProcessor;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(Optional<HibernateValidatorConfigurationStrategy> hibernateValidatorConfigurationStrategy,
                                                               List<? extends ConstraintValidator<?, ?>> validators) {
        var strategy = hibernateValidatorConfigurationStrategy.orElseGet(() -> configuration -> {
        });
        return new CustomLocalValidatorFactoryBean(strategy, validators);
    }

}
