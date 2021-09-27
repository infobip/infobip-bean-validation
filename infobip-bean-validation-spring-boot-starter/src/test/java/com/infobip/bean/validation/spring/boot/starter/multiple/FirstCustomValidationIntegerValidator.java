package com.infobip.bean.validation.spring.boot.starter.multiple;

import com.infobip.bean.validation.api.SimpleConstraintValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class FirstCustomValidationIntegerValidator implements SimpleConstraintValidator<FirstCustomValidation, Integer> {

    private final Consumer<Integer> integerConsumer;

    @Override
    public boolean isValid(Integer value) {
        integerConsumer.accept(value);
        return true;
    }
}
