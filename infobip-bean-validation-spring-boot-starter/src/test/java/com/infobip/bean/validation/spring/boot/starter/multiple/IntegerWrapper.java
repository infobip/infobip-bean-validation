package com.infobip.bean.validation.spring.boot.starter.multiple;

import lombok.Value;

@Value
class IntegerWrapper {

    @FirstCustomValidation
    private final Integer value;
}
