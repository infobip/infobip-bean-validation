package com.infobip.validation;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import org.aopalliance.aop.Advice;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

@SuppressWarnings("serial")
class CustomMethodValidationPostProcessor extends MethodValidationPostProcessor {

    private final ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper;

    CustomMethodValidationPostProcessor(ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper) {
        this.constraintViolationExceptionMapper = constraintViolationExceptionMapper;
    }

    @Override
    protected Advice createMethodValidationAdvice(Validator validator) {

        if (validator == null) {
            return new CustomMethodValidationInterceptor(constraintViolationExceptionMapper);
        }

        return new CustomMethodValidationInterceptor(validator, constraintViolationExceptionMapper);
    }
}
