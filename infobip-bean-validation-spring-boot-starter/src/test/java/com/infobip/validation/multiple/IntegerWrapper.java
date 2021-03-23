package com.infobip.validation.multiple;

import lombok.Value;

@Value
class IntegerWrapper {

    @FirstCustomValidation
    private final Integer value;
}
