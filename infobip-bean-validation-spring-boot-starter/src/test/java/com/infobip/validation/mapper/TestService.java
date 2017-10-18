package com.infobip.validation.mapper;

import javax.validation.constraints.NotNull;

public interface TestService {

    Object requireNonNull(@NotNull Object object);
}
