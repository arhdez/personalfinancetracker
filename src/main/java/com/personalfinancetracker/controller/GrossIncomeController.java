package com.personalfinancetracker.controller;

import com.personalfinancetracker.config.SecurityConfig;
import com.personalfinancetracker.dto.GrossIncomeDto;
import com.personalfinancetracker.service.GrossIncomeService;
import com.personalfinancetracker.validation.PatchGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/personalfinancetracker/grossincomes")
@Import(SecurityConfig.class)
@RequiredArgsConstructor
public class GrossIncomeController {

    private final GrossIncomeService grossIncomeService;

    @PostMapping
    public ResponseEntity<GrossIncomeDto> createGrossIncome(@Validated @RequestBody GrossIncomeDto newGrossIncomeRequiered) {
        return ResponseEntity.status(HttpStatus.CREATED).body(grossIncomeService.createGrossIncomeDto(newGrossIncomeRequiered));
    }

    @GetMapping("/search")
    public ResponseEntity<List<GrossIncomeDto>> search(
            @RequestParam(required = false) Double grossIncomeValue,
            @RequestParam(required = false) String filterType, // "greater", "less" or null for exact match
            @RequestParam(required = false) UUID personId,
            @RequestParam(required = false) String orderField,
            @RequestParam(required = false) String orderDirection,
            Pageable pageable
    ) {
        GrossIncomeDto request = new GrossIncomeDto();
        request.setGrossIncomeValue(grossIncomeValue);
        request.setPersonId(personId);

        return ResponseEntity.ok(grossIncomeService.searchGrossIncome(request, pageable, orderField, orderDirection, filterType));
    }

    @PatchMapping("/{requestedId}")
    public ResponseEntity<GrossIncomeDto> patchGrossIncome(@PathVariable UUID requestedId, @Validated(PatchGroup.class) @RequestBody GrossIncomeDto grossIncomeDto){
        Optional<GrossIncomeDto> grossIncomeDtoOptional = grossIncomeService.updateGrossIncome(grossIncomeDto, requestedId);
        return grossIncomeDtoOptional.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.noContent().build());
    }


    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteGrossIncome(@PathVariable UUID requestedId) {
        grossIncomeService.deleteGrossIncome(requestedId);
        return ResponseEntity.noContent().build();
    }
}
