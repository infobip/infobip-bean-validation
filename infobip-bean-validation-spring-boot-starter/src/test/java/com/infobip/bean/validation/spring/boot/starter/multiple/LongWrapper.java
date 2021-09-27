package com.infobip.bean.validation.spring.boot.starter.multiple;

import lombok.Value;

@Value
class LongWrapper {

    @FirstCustomValidation
    private final Long value;
}
