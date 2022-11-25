package com.infobip.validation.sequence;

import com.infobip.validation.sequences.Expensive;

import jakarta.validation.constraints.NotNull;

public class ExpensiveValidationModel {

    @NotNull
    private final String cheapString;

    @NotNull(groups = Expensive.class)
    private final String expensiveString;

    public ExpensiveValidationModel(String cheapString, String expensiveString) {
        this.cheapString = cheapString;
        this.expensiveString = expensiveString;
    }

    public String getCheapString() {
        return cheapString;
    }

    public String getExpensiveString() {
        return expensiveString;
    }
}
