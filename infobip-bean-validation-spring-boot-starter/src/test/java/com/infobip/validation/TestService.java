package com.infobip.validation;

import javax.validation.constraints.NotNull;

public interface TestService {

    Object requireNonNull(@NotNull Object object);
}
