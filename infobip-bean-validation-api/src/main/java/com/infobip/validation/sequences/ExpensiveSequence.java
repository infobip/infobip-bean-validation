package com.infobip.validation.sequences;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Expensive.class})
public interface ExpensiveSequence {
}
