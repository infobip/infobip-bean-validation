package com.infobip.bean.validation.spring.boot.starter.mapper;

import javax.validation.constraints.NotNull;

public interface TestService {

    Object requireNonNull(@NotNull Object object);
}
