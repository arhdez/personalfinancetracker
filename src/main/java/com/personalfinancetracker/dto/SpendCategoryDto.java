package com.personalfinancetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalfinancetracker.validation.CreateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SpendCategoryDto {

    @JsonProperty("category_id")
    private UUID categoryId;

    @JsonProperty("category_name")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String categoryName;
}
