package com.personalfinancetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personalfinancetracker.validation.CreateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PersonDto {

    @JsonProperty("person_id")
    private UUID personId;

    @JsonProperty("first_name")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String lastName;

    @JsonProperty("date_of_birth")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String dateOfBirth;

    @JsonProperty("email")
    @NotBlank(groups = CreateGroup.class)
    @NotNull(groups = CreateGroup.class)
    private String email;
}
