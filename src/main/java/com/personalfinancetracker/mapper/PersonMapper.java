package com.personalfinancetracker.mapper;

import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.jpa.Person;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = {LocalDateTime.class})
@DecoratedWith(PersonMapperDecorator.class)
public interface PersonMapper {

    @Mapping(target = "dateOfBirth", ignore = true)
    PersonDto personToPersonDto(Person person);

    @Mapping(target = "dateOfBirth", ignore = true)
    Person personDtoToPerson(PersonDto personDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dateOfBirth", ignore = true)
    void updatePerson(@MappingTarget Person existingPerson, PersonDto updatedPersonDto);
}
