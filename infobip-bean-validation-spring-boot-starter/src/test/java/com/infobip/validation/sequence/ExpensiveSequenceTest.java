package com.infobip.validation.sequence;

import com.infobip.validation.TestBase;
import com.infobip.validation.sequences.ExpensiveSequence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

public class ExpensiveSequenceTest extends TestBase {

    @Autowired
    private Validator validator;

    @Test
    void shouldValidateExpensiveConstraintIfDefaultValid() {
        // given
        ExpensiveValidationModel givenModel = new ExpensiveValidationModel("", null);

        // when
        Set<ConstraintViolation<ExpensiveValidationModel>> actual =
                validator.validate(givenModel, ExpensiveSequence.class);

        // then
        then(actual).extracting(cv -> cv.getPropertyPath().toString())
                    .containsExactly("expensiveString");
    }

    @Test
    void shouldNotValidateExpensiveConstraintIfDefaultNotValid() {
        // given
        ExpensiveValidationModel givenModel = new ExpensiveValidationModel(null, null);

        // when
        Set<ConstraintViolation<ExpensiveValidationModel>> actual =
                validator.validate(givenModel, ExpensiveSequence.class);

        // then
        then(actual).extracting(cv -> cv.getPropertyPath().toString())
                    .containsExactly("cheapString");
    }

}
