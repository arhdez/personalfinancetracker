package com.personalfinancetracker.mapper;

import com.personalfinancetracker.dto.GrossIncomeDto;
import com.personalfinancetracker.jpa.GrossIncome;
import org.mapstruct.*;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GrossIncomeMapper {

    GrossIncomeDto grossIncomeToGrossIncomeDto(GrossIncome grossIncome);

    GrossIncome grossIncomeDtoToGrossIncome(GrossIncomeDto grossIncomeDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGrossIncome(@MappingTarget GrossIncome existingGrossIncome, GrossIncomeDto updatedGrossIncomeDto);
}
