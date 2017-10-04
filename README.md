# Infobip Spring Bean Validation

[![Build Status](https://travis-ci.org/infobip/infobip-spring-bean-validation.svg?branch=master)](https://travis-ci.org/infobip/infobip-spring-bean-validation)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.infobip/infobip-spring-bean-validation-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.infobip/infobip-spring-bean-validation-boot-starter)

Library which provides new features on top of Hibernate Validator and Spring Boot Validation starter.

## Contents

1. [Requirements](#Requirements)
2. [Features and examples](#FeaturesAndExamples)
    * [Basic](#Basic)
    * [SimpleConstraintValidator](#SimpleConstraintValidator)
    * [ConstraintViolationException mapping](#ConstraintViolationExceptionMapping)
    * [Hibernate Validator Configuration Strategy](#HibernateValidatorConfigurationStrategy)
    * [Validator bean injection](#ValidatorBeanInjection)
    * [Decoupling of validation annotations and validators](#DecouplingOfValidationAnnotationsAndValidators)
3. [Contributing](#Contributing)
4. [License](#License)

## <a name="Requirements"></a> Requirements:

- Java 8
- Hibernate Validator
- Spring Boot (without Boot you will have to import BeanValidationAutoConfiguration manually)

## <a name="FeaturesAndExamples"></a> Features and examples:

### <a name="Basic"></a> Basic:

 - Auto configuration - no manual configuration required
 - predefined validation groups - Create, Update, Delete
 - Out of the box composed annotations: @ValidatedService, @ValidatedRestController
 
### <a name="ConstraintViolationExceptionMapping"></a> ConstraintViolationException mapping:

To remap all ConstraintViolationExceptions to a custom exception simply declare a bean of ConstraintViolationExceptionMapper type:

```java
@Bean
public ConstraintViolationExceptionMapper<IllegalArgumentException> constraintViolationExceptionMapper() {
    return e -> new IllegalArgumentException(e.getMessage());
}
```

### <a name="HibernateValidatorConfigurationStrategy"></a> Hibernate Validator Configuration Strategy:

By defining a bean of type HibernateConfigurationStrategy you can programmatically alter Hibernate Validator configuration:

```java
@Bean
public HibernateValidatorConfigurationStrategy hibernateValidatorConfigurationStrategy() {
    return configuration -> configuration.clockProvider(Clock::systemUTC);
}
```

### <a name="SimpleConstraintValidator"></a> SimpleConstraintValidator:
Bean Validation's `ConstraintValidator` defines two abstract methods, `initialize(A constraintAnnotation)` and 
`isValid(T value, ConstraintValidatorContext context)`.
For most cases, `initialize` is not used and is implemented as empty method. Also, `isValid` expects you to treat null 
value as valid. Both of these shortcomings are handled by `SimpleConstraintValidator` which is a `FunctionalInterface` 
(lambdas!) and only requires you to implement `isValid(T value)` (value passed to implementation is never null).
An example of an implementation using `ConstraintValidator`:

```java
@Component
public class FileNameValidator implements ConstraintValidator<FileName, String> {
 
    private static final char[] forbiddenCharacters = ...;
 
    @Override
    public void initialize(FileName constraintAnnotation) {
    }
 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
 
        return !StringUtils.containsAny(value, forbiddenCharacters);
    }
} 
```

As you can see, null check and initialize method implementation make a lot of useless noise.
Same example using SimpleConstraintValidator:

```java
@Component
public class FileNameValidator implements SimpleConstraintValidator<FileName, String> {
 
    private static final char[] forbiddenCharacters = ...;
 
    @Override
    public boolean isValid(String value) {
        return !StringUtils.containsAny(value, forbiddenCharacters);
    }
}
```

### <a name="ValidatorBeanInjection"></a> Validator bean injection:

This feature is provided by Spring and details can be found in [official Spring documenation](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validation-beanvalidation-spring-constraints).
Example:

```java
public class ValidExpirationTimeValidator implements SimpleConstraintValidator<ValidExpirationTime, Date> {
 
    private final Clock clock;
 
    public ValidExpirationTimeValidator(Clock clock) {
        this.clock = clock;
    }
 
    @Override
    public boolean isValid(Date value) {
 
        ... validation logic that uses injected clock
    }
}
```

### <a name="DecouplingOfValidationAnnotationsAndValidators"></a> Decoupling of validation annotations and validators:

This feature is provided by Hibernate Validator and details can be found in [official Hibernate Validator documentation](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-programmatic-constraint-definition).
Used for use cases where you don't want to provide a default implementation for your custom validation annotation and want to register it programmatically. For example, validator and validation annotation are in different artifacts.

For example, lets say we have a custom validation annotation MustNotBeTopSecret (note the empty validatedBy) and validator MustNotBeTopSecretValidator:

Annotation:
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {}) // EMPTY!
@Documented
public @interface MustNotBeTopSecret {
 
    String message() default "must not be top secret";
 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
}
```
Validator:
```
@Component
public class MustNotBeTopSecretValidator implements ConstraintValidator<MustNotBeTopSecret, String> {
 
    private final TopSecretService topSecretService;
 
    @Autowired
    public MustNotBeTopSecretValidator(TopSecretService topSecretService) {
        this.topSecretService = topSecretService;
    }
 
    ... isValid and initialize implementations
}
```

Custom validators are automatically injected into Hibernate Validator Configuration, here's an example of how you'd do it manually:

```java
@Bean
public HibernateConfigurationStrategy hibernateConfigurationStrategy() {
    return configuration -> configuration.createConstraintMapping()
                                         .constraintDefinition(MustNotBeTopSecret.class)
                                         .validatedBy(MustNotBeTopSecretValidator.class);
}
```

## <a name="Contributing"></a> Contributing

If you have an idea for a new feature or want to report a bug please use the issue tracker.

Pull requests are welcome!

## <a name="License"></a> License

This library is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).