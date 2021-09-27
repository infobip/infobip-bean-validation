package com.infobip.bean.validation.spring.boot.starter.api;

import javax.validation.ConstraintViolationException;
import java.util.function.Function;

@FunctionalInterface
public interface ConstraintViolationExceptionMapper<T extends Exception>
        extends Function<ConstraintViolationException, T> {
}
