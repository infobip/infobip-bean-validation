package com.infobip.validation.mapper;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomConstraintViolationExceptionMapperTest {

    @MockBean
    private ConstraintViolationExceptionMapper<?> mockMapper;

    @Autowired
    private CustomValidatedService customValidatedService;

    @Test
    void shouldUseCustomConstraintViolationExceptionMapper() {

        var thrown = catchThrowable(() -> customValidatedService.requireNonNull(null));

        then(thrown).isInstanceOf(RuntimeException.class);
        BDDMockito.then(mockMapper).should().apply(any());
    }
}
