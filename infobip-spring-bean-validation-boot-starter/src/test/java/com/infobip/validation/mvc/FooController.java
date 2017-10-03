package com.infobip.validation.mvc;

import com.infobip.validation.api.ValidatedRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;

@ValidatedRestController("bar")
public class FooController {

    @GetMapping
    public void get(@RequestParam(required = false) @Max(1) Integer bar) {
    }

    @ExceptionHandler
    private ResponseEntity<Void> handle(ConstraintViolationException e) {
        return ResponseEntity.badRequest().build();
    }
}
