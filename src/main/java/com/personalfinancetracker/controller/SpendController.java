package com.personalfinancetracker.controller;

import com.personalfinancetracker.config.SecurityConfig;
import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.service.SpendService;
import com.personalfinancetracker.validation.CreateGroup;
import com.personalfinancetracker.validation.PatchGroup;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/personalfinancetracker/spends")
@Import(SecurityConfig.class)
public class SpendController {

    private final SpendService spendService;

    public SpendController(SpendService spendService) {
        this.spendService = spendService;
    }

    @PostMapping
    public ResponseEntity<SpendDto> createSpend(@Validated(CreateGroup.class) @RequestBody SpendDto newSpendDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spendService.createSpendDto(newSpendDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SpendDto>> search(@RequestParam(required = false) List<String> keys,
                                                 @RequestParam(required = false) List<String> operations,
                                                 @RequestParam(required = false) List<String> values,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String orderField,
                                                 @RequestParam(defaultValue = "asc") String orderDirection) {
        return ResponseEntity.ok(spendService.searchByCriteria(keys, operations, values, page, size, orderField, orderDirection));
    }

    @PutMapping("/{requestedId}")
    public ResponseEntity<SpendDto> putSpend(@PathVariable UUID requestedId, @Validated(CreateGroup.class) @RequestBody SpendDto spendToUpdate){
        Optional<SpendDto> spendDtoOptional = spendService.updateSpend(spendToUpdate, requestedId);
        return spendDtoOptional.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PatchMapping("/{requestedId}")
    public ResponseEntity<SpendDto> patchSpend(@PathVariable UUID requestedId, @Validated(PatchGroup.class) @RequestBody SpendDto spendFieldToUpdate){
        Optional<SpendDto> spendDtoOptional = spendService.updateSpend(spendFieldToUpdate, requestedId);
        return spendDtoOptional.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{requestedId}")
    public ResponseEntity<Void> deleteSpend(@PathVariable UUID requestedId){
        spendService.deleteSpend(requestedId);
        return ResponseEntity.noContent().build();
    }

}
