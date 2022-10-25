package com.infobip.validation;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import org.aopalliance.aop.Advice;
import org.springframework.boot.validation.beanvalidation.FilteredMethodValidationPostProcessor;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;

import jakarta.validation.Validator;

import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("serial")
class CustomFilteredMethodValidationPostProcessor extends FilteredMethodValidationPostProcessor {

    private final ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper;

    CustomFilteredMethodValidationPostProcessor(Stream<? extends MethodValidationExcludeFilter> excludeFilters,
                                                ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper) {
        super(excludeFilters);
        this.constraintViolationExceptionMapper = constraintViolationExceptionMapper;
    }

    @Override
    protected Advice createMethodValidationAdvice(Supplier<Validator> validator) {

        return new CustomMethodValidationInterceptor(validator, constraintViolationExceptionMapper);
    }
}
