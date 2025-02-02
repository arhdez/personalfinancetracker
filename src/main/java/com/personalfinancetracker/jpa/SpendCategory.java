package com.personalfinancetracker.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class SpendCategory {

    @Id
    private UUID categoryId;
    private String categoryName;
}
