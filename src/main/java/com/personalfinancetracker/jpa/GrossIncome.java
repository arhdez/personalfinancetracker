package com.personalfinancetracker.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class GrossIncome {

    @Id
    private UUID grossIncomeId;
    private double grossIncomeValue;
    private UUID personId;
}
