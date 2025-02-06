package com.personalfinancetracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleValidator.class)
public @interface ValidDouble {
    String message() default "Invalid double format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payloads() default {};
}
