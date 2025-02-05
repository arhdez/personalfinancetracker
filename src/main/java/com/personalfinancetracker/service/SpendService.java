package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.mapper.SpendsMapper;
import com.personalfinancetracker.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;
    private final SpendsMapper spendsMapper;

    public SpendDto createSpendDto(SpendDto spendDto){
        //I have to add validation if existing
        return spendsMapper.spendsToSpendsDto(spendRepository.save(spendsMapper.spendsDtoToSpends(spendDto)));
    }
}
