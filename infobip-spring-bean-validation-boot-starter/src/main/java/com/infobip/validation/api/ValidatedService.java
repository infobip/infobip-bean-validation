package com.infobip.validation.api;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.*;

@Validated
@Service
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidatedService {
}