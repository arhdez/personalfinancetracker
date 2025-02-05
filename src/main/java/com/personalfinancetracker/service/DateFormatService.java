package com.personalfinancetracker.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateFormatService {
    public String localDateToString(LocalDate localDate) {
        // Check if the input date is not null
        if (localDate == null) {
            throw new IllegalArgumentException("Input date cannot be null");
        }
        // Format the date to the desired format
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public LocalDate stringToLocalDate(String dateString) {
        // Check if the input string is in the desired format
        if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }
        // Parse the input string to LocalDate
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
