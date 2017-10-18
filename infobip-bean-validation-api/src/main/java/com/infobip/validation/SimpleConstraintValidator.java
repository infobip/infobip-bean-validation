package com.infobip.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

@FunctionalInterface
public interface SimpleConstraintValidator<A extends Annotation, T> extends ConstraintValidator<A, T> {

    @Override
    default void initialize(A constraintAnnotation) {

    }

    @Override
    default boolean isValid(T value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return isValid(value);
    }

    boolean isValid(T value);
}
