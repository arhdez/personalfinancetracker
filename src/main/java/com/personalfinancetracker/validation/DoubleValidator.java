package com.personalfinancetracker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.DecimalFormat;
import java.text.ParseException;

public class DoubleValidator implements ConstraintValidator<ValidDouble, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) return false;

        // Example: Validate that the double has at most 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        try {
            return df.parse(df.format(value)).doubleValue() == value;
        } catch (ParseException e) {
            return false;
        }
    }
}
