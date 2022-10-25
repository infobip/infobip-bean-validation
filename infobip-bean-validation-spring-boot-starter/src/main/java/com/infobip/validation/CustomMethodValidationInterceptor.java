package com.infobip.validation;

import java.util.function.Supplier;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

class CustomMethodValidationInterceptor extends MethodValidationInterceptor {

    private final ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper;

    public CustomMethodValidationInterceptor(ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper) {
        this.constraintViolationExceptionMapper = constraintViolationExceptionMapper;
    }

    public CustomMethodValidationInterceptor(Supplier<Validator> validator,
                                             ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper) {
        super(validator);
        this.constraintViolationExceptionMapper = constraintViolationExceptionMapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return super.invoke(invocation);
        } catch (ConstraintViolationException e) {
            throw constraintViolationExceptionMapper.apply(e);
        }
    }
}
