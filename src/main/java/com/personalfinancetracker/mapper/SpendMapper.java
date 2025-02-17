package com.personalfinancetracker.mapper;

import com.personalfinancetracker.dto.SpendDto;
import com.personalfinancetracker.jpa.Spend;
import org.mapstruct.*;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpendMapper {

    SpendDto spendToSpendDto(Spend spend);

    Spend spendDtoToSpend(SpendDto spendDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpend(@MappingTarget Spend existingSpend, SpendDto updatedSpendDto);
}
