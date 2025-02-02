package com.personalfinancetracker.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Person {

    @Id
    private UUID personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /*@Column
    private byte[] ssn;*/

    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
}
