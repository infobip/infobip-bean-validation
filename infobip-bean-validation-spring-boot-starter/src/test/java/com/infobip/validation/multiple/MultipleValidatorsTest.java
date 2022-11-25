package com.infobip.validation.multiple;

import static org.mockito.BDDMockito.then;

import java.util.function.Consumer;

import com.infobip.validation.TestBase;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@AllArgsConstructor
public class MultipleValidatorsTest extends TestBase {

    private final Consumer<Integer> integerConsumer;
    private final Consumer<Long> longConsumer;
    private final Validator validator;

    @AfterEach
    void tearDown() {
        Mockito.reset(integerConsumer, longConsumer);
    }

    @Test
    void shouldValidateInteger() {

        // given
        Integer givenValue = 1;

        // when
        validator.validate(new IntegerWrapper(givenValue));

        // then
        then(integerConsumer).should().accept(givenValue);
        then(longConsumer).shouldHaveNoInteractions();
    }

    @Test
    void shouldValidateLong() {

        // given
        var givenValue = 2L;

        // when
        validator.validate(new LongWrapper(givenValue));

        // then
        then(longConsumer).should().accept(givenValue);
        then(integerConsumer).shouldHaveNoInteractions();
    }
}
