package com.infobip.bean.validation.spring.boot.starter.mvc;

import com.infobip.bean.validation.spring.boot.starter.api.ValidatedRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;

@ValidatedRestController
public class FooController {

    @GetMapping("/bar")
    public void get(@RequestParam(required = false) @Max(1) Integer bar) {
    }

    @ExceptionHandler
    private ResponseEntity<Void> handle(ConstraintViolationException e) {
        return ResponseEntity.badRequest().build();
    }
}
