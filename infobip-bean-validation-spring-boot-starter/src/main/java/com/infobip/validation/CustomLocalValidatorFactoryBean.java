package com.infobip.validation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import com.infobip.validation.api.HibernateValidatorConfigurationStrategy;
import jakarta.validation.Configuration;
import jakarta.validation.ConstraintValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.springframework.core.ResolvableType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

    private final HibernateValidatorConfigurationStrategy hibernateValidatorConfigurationStrategy;
    private final List<? extends ConstraintValidator<?, ?>> validators;

    CustomLocalValidatorFactoryBean(HibernateValidatorConfigurationStrategy hibernateValidatorConfigurationStrategy,
                                    List<? extends ConstraintValidator<?, ?>> validators) {
        this.hibernateValidatorConfigurationStrategy = hibernateValidatorConfigurationStrategy;
        this.validators = validators;
    }

    @Override
    protected void postProcessConfiguration(Configuration<?> configuration) {
        super.postProcessConfiguration(configuration);
        var hibernateConfiguration = (HibernateValidatorConfiguration) configuration;

        var constraintMapping = hibernateConfiguration.createConstraintMapping();
        validators.stream()
                  .collect(Collectors.groupingBy(this::extractValidationAnnotation))
                  .entrySet()
                  .forEach(entry -> addConstraint(constraintMapping, entry.getKey(), validators));
        hibernateConfiguration.addMapping(constraintMapping);

        hibernateValidatorConfigurationStrategy.accept(hibernateConfiguration);
    }

    private void addConstraint(ConstraintMapping constraintMapping,
                               Class<? extends Annotation> annotation,
                               List<? extends ConstraintValidator<? extends Annotation, ?>> validators) {
        var definition = constraintMapping.constraintDefinition(
                annotation);
        validators.stream()
                  .filter(validator -> extractValidationAnnotation(validator).equals(annotation))
                  .forEach(validator -> definition.validatedBy(getValidatorClass(validator)));
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Annotation> extractValidationAnnotation(ConstraintValidator<?, ?> validator) {

        if (validator instanceof SimpleConstraintValidator) {
            return (Class<? extends Annotation>) ResolvableType.forClass(validator.getClass())
                                                               .as(SimpleConstraintValidator.class)
                                                               .getGeneric(0)
                                                               .getRawClass();
        }

        return (Class<? extends Annotation>) ResolvableType.forClass(validator.getClass())
                                                           .as(ConstraintValidator.class).getGeneric(0).getRawClass();
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> Class<? extends ConstraintValidator<A, ?>> getValidatorClass(ConstraintValidator validator) {
        return (Class<? extends ConstraintValidator<A, ?>>) validator.getClass();
    }
}
