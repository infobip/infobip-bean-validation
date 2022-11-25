package com.infobip.validation.sequence;

import static org.assertj.core.api.BDDAssertions.then;

import com.infobip.validation.TestBase;
import com.infobip.validation.sequences.ExpensiveSequence;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExpensiveSequenceTest extends TestBase {

    @Autowired
    private Validator validator;

    @Test
    void shouldValidateExpensiveConstraintIfDefaultValid() {
        // given
        var givenModel = new ExpensiveValidationModel("", null);

        // when
        var actual =
                validator.validate(givenModel, ExpensiveSequence.class);

        // then
        then(actual).extracting(cv -> cv.getPropertyPath().toString())
                    .containsExactly("expensiveString");
    }

    @Test
    void shouldNotValidateExpensiveConstraintIfDefaultNotValid() {
        // given
        var givenModel = new ExpensiveValidationModel(null, null);

        // when
        var actual =
                validator.validate(givenModel, ExpensiveSequence.class);

        // then
        then(actual).extracting(cv -> cv.getPropertyPath().toString())
                    .containsExactly("cheapString");
    }

}
