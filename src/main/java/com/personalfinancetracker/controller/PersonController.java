package com.personalfinancetracker.controller;

import com.personalfinancetracker.config.SecurityConfig;
import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/personalfinancetracker/persons")
@RequiredArgsConstructor
@Import(SecurityConfig.class)
public class PersonController {
    private final PersonService personService;

    @GetMapping("/search")
    public ResponseEntity<List<PersonDto>> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String dateOfBirth,
            // New dynamic ordering parameters:
            @RequestParam(required = false) String orderField,
            @RequestParam(required = false) String orderDirection,
            Pageable pageable
    ) {
        PersonDto request = new PersonDto();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setDateOfBirth(dateOfBirth);

        return ResponseEntity.ok(personService.searchPeople(request, pageable, orderField, orderDirection));
    }
}
