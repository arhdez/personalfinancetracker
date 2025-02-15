package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.mapper.SpendsMapper;
import com.personalfinancetracker.repository.SpendRepository;
import com.personalfinancetracker.validation.DuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;
    private final SpendsMapper spendsMapper;

    public SpendDto createSpendDto(SpendDto spendDto){
        if (spendRepository.existsBySpendAmountAndDateSpendAndPersonIdAndCategoryIdAndSpendDescription(spendDto.getSpendAmount(),
                LocalDate.parse(spendDto.getDateSpend()), spendDto.getPersonId(), spendDto.getCategoryId() ,spendDto.getSpendDescription())){
            throw new DuplicateException("Spend with description: " + spendDto.getSpendDescription() + " is already in the system.");
        }
        return spendsMapper.spendsToSpendsDto(spendRepository.save(spendsMapper.spendsDtoToSpends(spendDto)));
    }


}
