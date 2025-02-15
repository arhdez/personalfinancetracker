package com.personalfinancetracker.controller;

import com.personalfinancetracker.config.SecurityConfig;
import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.service.SpendService;
import com.personalfinancetracker.validation.CreateGroup;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personalfinancetracker/spends")
@Import(SecurityConfig.class)
public class SpendController {

    private final SpendService spendService;
    public SpendController(SpendService spendService) {
        this.spendService = spendService;
    }

    @PostMapping
    public ResponseEntity<SpendDto> createSpend(@Validated(CreateGroup.class) @RequestBody SpendDto newSpendDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(spendService.createSpendDto(newSpendDto));
    }
}
