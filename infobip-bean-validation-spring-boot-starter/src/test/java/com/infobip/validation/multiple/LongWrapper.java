package com.infobip.validation.multiple;

import lombok.Value;

@Value
class LongWrapper {

    @CustomValidation
    private final Long value;
}
