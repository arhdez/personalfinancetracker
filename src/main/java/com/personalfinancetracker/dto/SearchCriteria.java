package com.personalfinancetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String key;       // Field name
    private String operation; // e.g., ">", "<", "="
    private String value;     // Value to compare
}
