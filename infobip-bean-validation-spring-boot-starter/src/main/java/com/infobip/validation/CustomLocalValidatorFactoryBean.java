package com.infobip.validation;

import com.infobip.validation.api.HibernateValidatorConfigurationStrategy;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.springframework.core.ResolvableType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Configuration;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

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
        HibernateValidatorConfiguration hibernateConfiguration = (HibernateValidatorConfiguration) configuration;

        ConstraintMapping constraintMapping = hibernateConfiguration.createConstraintMapping();
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
        ConstraintDefinitionContext<? extends Annotation> definition = constraintMapping.constraintDefinition(
                annotation);
        validators.forEach(validator -> definition.validatedBy(getValidatorClass(validator)));
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
