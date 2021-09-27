package com.infobip.bean.validation.spring.boot.starter.api;

import org.springframework.core.annotation.AliasFor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Validated
@RestController
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedRestController {

    @AliasFor(annotation = RestController.class, attribute = "value")
    String value() default "";
}
