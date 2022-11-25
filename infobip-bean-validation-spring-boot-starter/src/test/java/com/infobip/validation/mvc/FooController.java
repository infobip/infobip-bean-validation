package com.infobip.validation.mvc;

import com.infobip.validation.api.ValidatedRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;

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
