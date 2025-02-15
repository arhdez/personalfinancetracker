package com.personalfinancetracker.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Spend {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID spendId;

    @Column(name = "spend_amount")
    private Double spendAmount;

    @Column(name = "date_spend")
    private LocalDate dateSpend;

    @Column(name = "person_id")
    private UUID personId;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "spend_description")
    private String spendDescription;
}
