package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.GrossIncomeDto;
import com.personalfinancetracker.mapper.GrossIncomeMapper;
import com.personalfinancetracker.repository.GrossIncomeRespository;
import com.personalfinancetracker.repository.PersonRepository;
import com.personalfinancetracker.validation.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrossIncomeService {

    private final GrossIncomeRespository grossIncomeRespository;
    private final GrossIncomeMapper grossIncomeMapper;
    private final PersonRepository personRepository;

    public GrossIncomeDto createGrossIncomeDto(GrossIncomeDto grossIncomeDto){
        if (grossIncomeRespository.existsByPersonId(grossIncomeDto.getPersonId())){
            throw new DuplicateException("Gross Income from: " + personRepository.findById(grossIncomeDto.getPersonId()) + " is already in the system.");
        }
        return grossIncomeMapper.grossIncomeToGrossIncomeDto(grossIncomeRespository.save(grossIncomeMapper.grossIncomeDtoToGrossIncome(grossIncomeDto)));
    }
}
