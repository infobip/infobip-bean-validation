package com.infobip.bean.validation.spring.boot.starter.sequence;

import com.infobip.bean.validation.api.sequences.ExpensiveSequence;
import com.infobip.bean.validation.spring.boot.starter.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

import static org.assertj.core.api.BDDAssertions.then;

public class ExpensiveSequenceTest extends TestBase {

    @Autowired
    private Validator validator;

    @Test
    void shouldValidateExpensiveConstraintIfDefaultValid() {
        // given
        var givenModel = new ExpensiveValidationModel("", null);

        // when
        var actual = validator.validate(givenModel, ExpensiveSequence.class);

        // then
        then(actual).extracting(cv -> cv.getPropertyPath().toString())
                    .containsExactly("expensiveString");
    }

    @Test
    void shouldNotValidateExpensiveConstraintIfDefaultNotValid() {
        // given
        var givenModel = new ExpensiveValidationModel(null, null);

        // when
        var actual = validator.validate(givenModel, ExpensiveSequence.class);

        // then
        then(actual).extracting(cv -> cv.getPropertyPath().toString())
                    .containsExactly("cheapString");
    }
}
