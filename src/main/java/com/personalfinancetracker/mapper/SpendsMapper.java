package com.personalfinancetracker.mapper;

import com.personalfinancetracker.dto.SpendsDto;
import com.personalfinancetracker.jpa.Spends;
import org.mapstruct.*;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpendsMapper {

    SpendsDto spendsToSpendsDto(Spends spends);

    Spends spendsDtoToSpends(SpendsDto spendsDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpends(@MappingTarget Spends existingSpends, SpendsDto updatedSpendsDto);
}
