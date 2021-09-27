package com.infobip.bean.validation.api.sequences;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Expensive.class})
public interface ExpensiveSequence {
}
