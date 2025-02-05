package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.SpendCategoryDto;
import com.personalfinancetracker.jpa.SpendCategory;
import com.personalfinancetracker.mapper.SpendCategoryMapper;
import com.personalfinancetracker.repository.SpendCategoryRepository;
import com.personalfinancetracker.validation.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpendCategoryService {

    private final SpendCategoryRepository spendCategoryRepository;
    private final SpendCategoryMapper spendCategoryMapper;

    public SpendCategoryDto createSpendCategory(SpendCategoryDto spendCategoryDto) {
        if (spendCategoryRepository.existsByCategoryName(spendCategoryDto.getCategoryName())) {
            throw new DuplicateException("Spending category: " + spendCategoryDto.getCategoryName() + " already exists.");
        }
        return spendCategoryMapper.spendCategoryToSpendCategoryDto(spendCategoryRepository.save(spendCategoryMapper.spendCategoryDtoToSpendCategory(spendCategoryDto)));
    }

    public List<SpendCategoryDto> findAllSpendCategory(Pageable pageable){
        PageRequest pageRequest =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "categoryName")));
        return spendCategoryRepository.findAll(pageRequest).stream().map(spendCategoryMapper::spendCategoryToSpendCategoryDto).collect(Collectors.toList());
    }
    public Optional<SpendCategoryDto> findById(UUID spendCategoryId){
        return spendCategoryRepository.findById(spendCategoryId).map(spendCategoryMapper::spendCategoryToSpendCategoryDto);
    }
    public Optional<SpendCategoryDto> findByCategoryName(String categoryName){
        return spendCategoryRepository.findByCategoryName(categoryName).map(spendCategoryMapper::spendCategoryToSpendCategoryDto);
    }

    public Optional<SpendCategoryDto> updateSpendCategory(SpendCategoryDto spendCategoryDto, UUID spendCategoryId){
        Optional<SpendCategory> existingSpendCategoryOptional = spendCategoryRepository.findById(spendCategoryId);
        if (existingSpendCategoryOptional.isPresent()){
            SpendCategory existingSpendCategory = existingSpendCategoryOptional.get();
            spendCategoryMapper.updateSpendCategory(existingSpendCategory, spendCategoryDto);
            return Optional.of(spendCategoryMapper.spendCategoryToSpendCategoryDto(spendCategoryRepository.save(existingSpendCategory)));
        }
        return Optional.empty();
    }

    public void deleteCategorySpending(UUID requestedId){spendCategoryRepository.deleteById(requestedId);}
}
