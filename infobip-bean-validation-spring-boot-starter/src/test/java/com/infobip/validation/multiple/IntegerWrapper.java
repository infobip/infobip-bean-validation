package com.infobip.validation.multiple;

import lombok.Value;

@Value
class IntegerWrapper {

    @CustomValidation
    private final Integer value;
}
