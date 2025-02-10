package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.GrossIncomeDto;
import com.personalfinancetracker.jpa.GrossIncome;
import com.personalfinancetracker.mapper.GrossIncomeMapper;
import com.personalfinancetracker.model.GrossIncomeSpecification;
import com.personalfinancetracker.repository.GrossIncomeRepository;
import com.personalfinancetracker.repository.PersonRepository;
import com.personalfinancetracker.validation.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GrossIncomeService {

    private final GrossIncomeRepository grossIncomeRepository;
    private final GrossIncomeMapper grossIncomeMapper;
    private final PersonRepository personRepository;

    public GrossIncomeDto createGrossIncomeDto(GrossIncomeDto grossIncomeDto) {
        if (grossIncomeRepository.existsByPersonId(grossIncomeDto.getPersonId())) {
            throw new DuplicateException("Gross Income from: " + personRepository.findById(grossIncomeDto.getPersonId()) + " is already in the system.");
        }
        return grossIncomeMapper.grossIncomeToGrossIncomeDto(grossIncomeRepository.save(grossIncomeMapper.grossIncomeDtoToGrossIncome(grossIncomeDto)));
    }

    public List<GrossIncomeDto> searchGrossIncome(GrossIncomeDto request, Pageable pageable,
                                                  String orderField, String orderDirection) {
        Specification<GrossIncome> specification = GrossIncomeSpecification.search(request);

        PageRequest pageRequest = createPageRequest(pageable, orderField, orderDirection);

        Page<GrossIncome> grossIncomes = grossIncomeRepository.findAll(specification, pageRequest);
        return grossIncomes.stream().map(grossIncomeMapper::grossIncomeToGrossIncomeDto).toList();
    }

    public Optional<GrossIncomeDto> updateGrossIncome(GrossIncomeDto updatedGrossIncomeDto, UUID requestedId) {
        Optional<GrossIncome> existingGrossIncomeOptional = grossIncomeRepository.findById(requestedId);
        Optional<GrossIncome> temp = grossIncomeRepository.findByPersonId(updatedGrossIncomeDto.getPersonId());
        if (temp.isPresent() && temp.get().getGrossIncomeId() != existingGrossIncomeOptional.get().getGrossIncomeId()){
            throw new DuplicateException("Gross Income from: " + personRepository.findById(updatedGrossIncomeDto.getPersonId()) + " is already in the system.");
        }
        if (existingGrossIncomeOptional.isPresent()) {
            GrossIncome existingGrossIncome = existingGrossIncomeOptional.get();
            grossIncomeMapper.updateGrossIncome(existingGrossIncome, updatedGrossIncomeDto);
            return Optional.of(grossIncomeMapper.grossIncomeToGrossIncomeDto(grossIncomeRepository.save(existingGrossIncome)));
        }
        return Optional.empty();
    }

    public void deleteGrossIncome(UUID requestedId) {
        grossIncomeRepository.deleteById(requestedId);
    }

    private PageRequest createPageRequest(Pageable pageable, String orderField, String orderDirection) {
        Sort sort = pageable.getSort();

        if (!sort.isSorted() && orderField != null && !orderField.isEmpty()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if (orderDirection != null && orderDirection.equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
            sort = Sort.by(direction, orderField);
        }
        if (!sort.isSorted()) {
            sort = Sort.by(Sort.Direction.ASC, "grossIncomeValue");
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

}
