package com.personalfinancetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalfinancetracker.validation.CreateGroup;
import com.personalfinancetracker.validation.ValidDouble;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class GrossIncomeDto {

    @JsonProperty("gross_income_id")
    private UUID grossIncomeId;

    @JsonProperty("gross_income")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    @ValidDouble
    private Double grossIncome;

    @JsonProperty("person_id")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private UUID PersonId;
}
