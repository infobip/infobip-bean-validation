package com.infobip.validation.mapper;

import jakarta.validation.constraints.NotNull;

public interface TestService {

    Object requireNonNull(@NotNull Object object);
}
