package com.infobip.validation.multiple;

import lombok.Value;

@Value
class LongWrapper {

    @FirstCustomValidation
    private final Long value;
}
