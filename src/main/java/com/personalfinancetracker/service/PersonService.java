package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.jpa.Person;
import com.personalfinancetracker.mapper.PersonMapper;
import com.personalfinancetracker.model.PersonSpecification;
import com.personalfinancetracker.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public List<PersonDto> searchPeople(PersonDto request) {
        Specification<Person> specification = PersonSpecification.search(request);
        List<Person> people = personRepository.findAll(specification);
        return people.stream()
                .map(personMapper::personToPersonDto)
                .toList();
    }

    public void processPerson(PersonDto personDto){
        if (personRepository.existsById(personDto.getPersonId())){
            updatePerson(personDto, personDto.getPersonId());
        }else{
            createPerson(personDto);
        }
    }

    public PersonDto createPerson(PersonDto personDto){
        return personMapper.personToPersonDto(personRepository.save(personMapper.personDtoToPerson(personDto)));
    }

    public Optional<PersonDto> updatePerson(PersonDto personDto, UUID requestedId){
        Optional<Person> existingPersonOptional = personRepository.findById(requestedId);
        if (existingPersonOptional.isPresent()){
            Person existingPerson = existingPersonOptional.get();
            personMapper.updatePerson(existingPerson, personDto);
            return Optional.of(personMapper.personToPersonDto(personRepository.save(existingPerson)));
        }
        return Optional.empty();
    }

}
