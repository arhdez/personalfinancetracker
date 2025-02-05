package com.personalfinancetracker.mapper;


import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.jpa.Person;
import com.personalfinancetracker.service.DateFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class PersonMapperDecorator implements  PersonMapper{

    @Autowired
    @Qualifier("delegate")
    PersonMapper delegate;

    @Autowired
    DateFormatService dateFormatService;

    @Override
    public PersonDto personToPersonDto(Person person) {
        PersonDto personDto = delegate.personToPersonDto(person);
        personDto.setDateOfBirth(dateFormatService.localDateToString(person.getDateOfBirth()));
        return personDto;
    }

    @Override
    public Person personDtoToPerson(PersonDto personDto) {
        Person person = delegate.personDtoToPerson(personDto);
        person.setDateOfBirth(dateFormatService.stringToLocalDate(personDto.getDateOfBirth()));
        return person;
    }

    @Override
    public void updatePerson(Person existent, PersonDto updatedPersonDto) {
        delegate.updatePerson(existent, updatedPersonDto);

        if (updatedPersonDto.getDateOfBirth() != null) {
            existent.setDateOfBirth(dateFormatService.stringToLocalDate(updatedPersonDto.getDateOfBirth()));
        }
    }
}
