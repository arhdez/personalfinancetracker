package com.personalfinancetracker.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Spends {

    @Id
    private UUID spendId;
    private String spendDescription;
    private Double spendAmount;
    private LocalDate date;
    private UUID personId;
    private UUID categoryId;
}
