package com.infobip.validation.sequences;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, Expensive.class})
public interface ExpensiveSequence {
}
