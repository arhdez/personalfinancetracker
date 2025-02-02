package com.personalfinancetracker.mapper;

import com.personalfinancetracker.dto.SpendCategoryDto;
import com.personalfinancetracker.jpa.SpendCategory;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpendCategoryMapper {

    SpendCategoryDto spendCategoryToSpendCategoryDto(SpendCategory spendCategory);

    SpendCategory spendCategoryDtoToSpendCategory(SpendCategoryDto spendCategoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSpendCategory(@MappingTarget SpendCategory existingSpendCategory, SpendCategoryDto updatedSpendCategoryDto);
}
