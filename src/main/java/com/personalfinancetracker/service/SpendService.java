package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.SearchCriteria;
import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.jpa.Spend;
import com.personalfinancetracker.mapper.SpendMapper;
import com.personalfinancetracker.model.GenericSpecification;
import com.personalfinancetracker.repository.SpendRepository;
import com.personalfinancetracker.validation.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;
    private final SpendMapper spendMapper;

    public SpendDto createSpendDto(SpendDto spendDto) {
        if (spendRepository.existsBySpendAmountAndDateSpendAndPersonIdAndCategoryIdAndSpendDescription(spendDto.getSpendAmount(),
                LocalDate.parse(spendDto.getDateSpend()), spendDto.getPersonId(), spendDto.getCategoryId(), spendDto.getSpendDescription())) {
            throw new DuplicateException("Spend with description: " + spendDto.getSpendDescription() + " is already in the system.");
        }
        return spendMapper.spendToSpendDto(spendRepository.save(spendMapper.spendDtoToSpend(spendDto)));
    }

    public List<SpendDto> search(List<SearchCriteria> criteriaList) {
        Specification<Spend> specification = Specification.where(null);

        for (SearchCriteria criteria : criteriaList) {
            specification = specification.and(new GenericSpecification<>(criteria));
        }
        return spendRepository.findAll(specification)
                .stream().map(spendMapper::spendToSpendDto)
                .collect(Collectors.toList());
    }

    public List<SpendDto> searchByCriteria(List<String> keys, List<String> operations, List<String> values, int page, int size, String orderField, String orderDirection) {
        Pageable pageable = PageRequest.of(page, size, getSort(orderField, orderDirection));
        // If no filters are provided, return all records
        if (keys == null || operations == null || values == null) {
            return getAllSpends(pageable);
        }

        if (keys.size() != operations.size() || keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys, operations, and values must have the same size");
        }

        List<SearchCriteria> criteriaList = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            criteriaList.add(new SearchCriteria(keys.get(i), operations.get(i), values.get(i)));
        }

        return search(criteriaList);  // Calls the existing search method
    }

    public List<SpendDto> getAllSpends(Pageable pageable) {
        return spendRepository.findAll(pageable)
                .stream()
                .map(spendMapper::spendToSpendDto)
                .collect(Collectors.toList());
    }

    public Optional<SpendDto> updateSpend(SpendDto spentToUpdateDto, UUID requestedId) {
        return spendRepository.findById(requestedId)
                .map(existentSpend -> {
                    spendMapper.updateSpend(existentSpend, spentToUpdateDto);
                    return spendMapper.spendToSpendDto(spendRepository.save(existentSpend));
                });
    }

    public void deleteSpend(UUID requestedId){spendRepository.deleteById(requestedId);}

    private Sort getSort(String orderField, String orderDirection) {
        if (orderField != null && !orderField.isEmpty()) {
            return "desc".equalsIgnoreCase(orderDirection) ? Sort.by(orderField).descending() : Sort.by(orderField).ascending();
        }
        return Sort.unsorted();
    }

}
