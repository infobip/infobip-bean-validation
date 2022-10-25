package com.infobip.validation.api;

import jakarta.validation.ConstraintViolationException;
import java.util.function.Function;

@FunctionalInterface
public interface ConstraintViolationExceptionMapper<T extends Exception>
        extends Function<ConstraintViolationException, T> {
}
