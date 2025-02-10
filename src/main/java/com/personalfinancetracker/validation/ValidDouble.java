package com.personalfinancetracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DoubleValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDouble {
    String message() default "Invalid double format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
