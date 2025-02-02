package com.personalfinancetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalfinancetracker.validation.CreateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpendsDto {

    @JsonProperty("spend_id")
    private UUID spendId;

    @JsonProperty("spend_description")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String spendDescription;

    @JsonProperty("spend_amount")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private Double spendAmount;

    @JsonProperty("date_of_birth")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String date;

    @JsonProperty("person_id")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private UUID personId;

    @JsonProperty("category_id")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private UUID categoryId;
}
