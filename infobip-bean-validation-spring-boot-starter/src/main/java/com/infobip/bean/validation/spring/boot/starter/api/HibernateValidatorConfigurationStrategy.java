package com.infobip.bean.validation.spring.boot.starter.api;

import org.hibernate.validator.HibernateValidatorConfiguration;

import java.util.function.Consumer;

@FunctionalInterface
public interface HibernateValidatorConfigurationStrategy extends Consumer<HibernateValidatorConfiguration> {
}
