package com.infobip.bean.validation.spring.boot.starter;

import com.infobip.bean.validation.spring.boot.starter.api.ConstraintViolationExceptionMapper;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

class CustomMethodValidationInterceptor extends MethodValidationInterceptor {

    private final ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper;

    public CustomMethodValidationInterceptor(ConstraintViolationExceptionMapper<?> constraintViolationExceptionMapper) {
        this.constraintViolationExceptionMapper = constraintViolationExceptionMapper;
    }

    public CustomMethodValidationInterceptor(Validator validator,
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
