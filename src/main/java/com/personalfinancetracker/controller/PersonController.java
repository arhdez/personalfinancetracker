package com.personalfinancetracker.controller;

import com.personalfinancetracker.config.SecurityConfig;
import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
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
    public ResponseEntity<List<PersonDto>> search(@RequestParam(required = false) List<String> keys,
                                                  @RequestParam(required = false) List<String> operations,
                                                  @RequestParam(required = false) List<String> values,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false) String orderField,
                                                  @RequestParam(defaultValue = "asc") String orderDirection) {
        return ResponseEntity.ok(personService.searchByCriteria(keys, operations, values, page, size, orderField, orderDirection));
    }

}
