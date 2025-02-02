package com.personalfinancetracker.mapper;

import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.jpa.Spend;
import org.mapstruct.*;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpendsMapper {

    SpendDto spendsToSpendsDto(Spend spend);

    Spend spendsDtoToSpends(SpendDto spendDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpends(@MappingTarget Spend existingSpend, SpendDto updatedSpendDto);
}
