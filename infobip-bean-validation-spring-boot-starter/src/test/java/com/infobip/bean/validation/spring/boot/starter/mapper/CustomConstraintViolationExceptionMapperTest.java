package com.infobip.bean.validation.spring.boot.starter.mapper;

import com.infobip.bean.validation.spring.boot.starter.api.ConstraintViolationExceptionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Matchers.any;

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

        var actual = catchThrowable(() -> customValidatedService.requireNonNull(null));

        then(actual).isInstanceOf(RuntimeException.class);
        BDDMockito.then(mockMapper).should().apply(any());
    }
}
