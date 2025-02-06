package com.personalfinancetracker.controller;

import com.personalfinancetracker.config.SecurityConfig;
import com.personalfinancetracker.dto.SpendCategoryDto;
import com.personalfinancetracker.service.SpendCategoryService;
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
@RequestMapping("/personalfinancetracker/spendcategories")
@Import(SecurityConfig.class)
public class SpendCategoryController {

    private final SpendCategoryService spendCategoryService;

    public SpendCategoryController(SpendCategoryService spendCategoryService) {
        this.spendCategoryService = spendCategoryService;
    }

    @PostMapping
    public ResponseEntity<SpendCategoryDto> createSpendCategory(@Validated @RequestBody SpendCategoryDto newSpendCategoryRequired) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spendCategoryService.createSpendCategory(newSpendCategoryRequired));
    }

    @GetMapping
    public ResponseEntity<List<SpendCategoryDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(spendCategoryService.findAllSpendCategory(pageable));
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<SpendCategoryDto> findSpendCategoryByName(@PathVariable String categoryName) {
        Optional<SpendCategoryDto> result = spendCategoryService.findByCategoryName(categoryName);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{requestedId}")
    public ResponseEntity<SpendCategoryDto> updateSpendCategory(@PathVariable UUID requestedId, @Validated @RequestBody SpendCategoryDto spendCategoryDto){
        Optional<SpendCategoryDto> spendCategoryDtoOptional = spendCategoryService.updateSpendCategory(spendCategoryDto, requestedId);
        return spendCategoryDtoOptional.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteSpendCategory(@PathVariable UUID requestedId){
        spendCategoryService.deleteCategorySpending(requestedId);
        return ResponseEntity.notFound().build();
    }


}
