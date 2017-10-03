package com.infobip.validation.mapper;

import com.infobip.validation.api.ConstraintViolationExceptionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomConstraintViolationExceptionMapperTest {

    @MockBean
    private ConstraintViolationExceptionMapper<?> mockMapper;

    @Autowired
    private CustomValidatedService customValidatedService;

    @Test
    public void shouldUseCustomConstraintViolationExceptionMapper() {

        Throwable thrown = catchThrowable(() -> customValidatedService.requireNonNull(null));

        then(thrown).isInstanceOf(RuntimeException.class);
        BDDMockito.then(mockMapper).should().apply(any());
    }
}
