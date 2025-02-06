package com.personalfinancetracker.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class GrossIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID grossIncomeId;

    @Column(name = "gross_income_value")
    private double grossIncomeValue;

    @Column(name = "person_id")
    private UUID personId;
}
