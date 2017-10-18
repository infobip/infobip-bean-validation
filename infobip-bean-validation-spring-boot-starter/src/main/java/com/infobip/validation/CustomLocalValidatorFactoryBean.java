package com.infobip.validation;

import com.infobip.validation.api.HibernateValidatorConfigurationStrategy;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Configuration;
import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

    private final HibernateValidatorConfigurationStrategy hibernateValidatorConfigurationStrategy;
    private final List<? extends ConstraintValidator> validators;

    CustomLocalValidatorFactoryBean(HibernateValidatorConfigurationStrategy hibernateValidatorConfigurationStrategy,
                                    List<? extends ConstraintValidator> validators) {
        this.hibernateValidatorConfigurationStrategy = hibernateValidatorConfigurationStrategy;
        this.validators = validators;
    }

    @Override
    protected void postProcessConfiguration(Configuration<?> configuration) {
        super.postProcessConfiguration(configuration);
        HibernateValidatorConfiguration hibernateConfiguration = (HibernateValidatorConfiguration) configuration;

        ConstraintMapping constraintMapping = hibernateConfiguration.createConstraintMapping();
        validators.forEach(validator -> addConstraint(constraintMapping, validator));
        hibernateConfiguration.addMapping(constraintMapping);

        hibernateValidatorConfigurationStrategy.accept(hibernateConfiguration);
    }

    private void addConstraint(ConstraintMapping mapping, ConstraintValidator validator) {
        Stream.of(validator.getClass().getGenericInterfaces())
              .filter(genericInterface -> genericInterface instanceof ParameterizedType)
              .map(genericInterface -> (ParameterizedType) genericInterface)
              .filter(genericInterface -> isConstraintValidator(genericInterface.getRawType()))
              .forEach(constraintValidatorInterface -> {
                  @SuppressWarnings("unchecked")
                  Class<? extends Annotation> a = (Class<? extends Annotation>) constraintValidatorInterface.getActualTypeArguments()[0];
                  mapping.constraintDefinition(a).validatedBy(getValidatorClass(validator));
              });
    }

    private boolean isConstraintValidator(Type type) {
        return type.equals(ConstraintValidator.class) || type.equals(SimpleConstraintValidator.class);
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> Class<? extends ConstraintValidator<A, ?>> getValidatorClass(ConstraintValidator validator) {
        return (Class<? extends ConstraintValidator<A, ?>>) validator.getClass();
    }
}
