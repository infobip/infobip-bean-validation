package com.infobip.validation.api;

import org.hibernate.validator.HibernateValidatorConfiguration;

import java.util.function.Consumer;

@FunctionalInterface
public interface HibernateValidatorConfigurationStrategy extends Consumer<HibernateValidatorConfiguration> {
}
