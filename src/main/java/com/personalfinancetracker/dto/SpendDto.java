package com.personalfinancetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalfinancetracker.validation.CreateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpendDto {

    @JsonProperty("spend_id")
    private UUID spendId;

    @JsonProperty("spend_amount")
    @NotNull(groups = CreateGroup.class)
    private Double spendAmount;

    @JsonProperty("date_spend")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String dateSpend;

    @JsonProperty("person_id")
    @NotNull(groups = CreateGroup.class)
    private UUID personId;

    @JsonProperty("category_id")
    @NotNull(groups = CreateGroup.class)
    private UUID categoryId;

    @JsonProperty("spend_description")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String spendDescription;
}
