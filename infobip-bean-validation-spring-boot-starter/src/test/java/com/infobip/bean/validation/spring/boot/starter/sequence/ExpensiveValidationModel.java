package com.infobip.bean.validation.spring.boot.starter.sequence;

import com.infobip.bean.validation.api.sequences.Expensive;

import javax.validation.constraints.NotNull;

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
