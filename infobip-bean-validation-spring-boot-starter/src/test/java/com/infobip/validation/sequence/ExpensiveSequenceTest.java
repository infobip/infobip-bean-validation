package com.infobip.validation.sequence;

import com.infobip.validation.TestBase;
import com.infobip.validation.sequences.ExpensiveSequence;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;

public class ExpensiveSequenceTest extends TestBase {

    @Autowired
    private Validator validator;

    @Test
    public void shouldValidateExpensiveConstraintIfDefaultValid() throws Exception {
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
    public void shouldNotValidateExpensiveConstraintIfDefaultNotValid() throws Exception {
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
