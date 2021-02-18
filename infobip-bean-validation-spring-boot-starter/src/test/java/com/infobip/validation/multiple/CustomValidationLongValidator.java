package com.infobip.validation.multiple;

import com.infobip.validation.SimpleConstraintValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class CustomValidationLongValidator implements SimpleConstraintValidator<CustomValidation, Long> {

    private final Consumer<Long> longConsumer;

    @Override
    public boolean isValid(Long value) {
        longConsumer.accept(value);
        return true;
    }
}
