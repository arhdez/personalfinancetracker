package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.jpa.Person;
import com.personalfinancetracker.mapper.PersonMapper;
import com.personalfinancetracker.model.PersonSpecification;
import com.personalfinancetracker.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public List<PersonDto> searchPeople(PersonDto request, Pageable pageable,
                                        String orderField, String orderDirection) {
        Specification<Person> specification = PersonSpecification.search(request);

        PageRequest pageRequest = createPageRequest(pageable, orderField, orderDirection);

        Page<Person> personPage = personRepository.findAll(specification, pageRequest);
        return personPage.getContent().stream().map(personMapper::personToPersonDto).collect(Collectors.toList());
    }

    public void processPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getPersonId())) {
            updatePerson(personDto, personDto.getPersonId());
        } else {
            createPerson(personDto);
        }
    }

    public PersonDto createPerson(PersonDto personDto) {
        return personMapper.personToPersonDto(personRepository.save(personMapper.personDtoToPerson(personDto)));
    }

    public Optional<PersonDto> updatePerson(PersonDto personDto, UUID requestedId) {
        Optional<Person> existingPersonOptional = personRepository.findById(requestedId);
        if (existingPersonOptional.isPresent()) {
            Person existingPerson = existingPersonOptional.get();
            personMapper.updatePerson(existingPerson, personDto);
            return Optional.of(personMapper.personToPersonDto(personRepository.save(existingPerson)));
        }
        return Optional.empty();
    }

    private PageRequest createPageRequest(Pageable pageable, String orderField, String orderDirection) {
        Sort sort = pageable.getSort();

        if (!sort.isSorted() && orderField != null && !orderField.isEmpty()) {
            Sort.Direction direction = Sort.Direction.ASC;
            if (orderDirection != null && orderDirection.equalsIgnoreCase("desc")) {
                direction = Sort.Direction.DESC;
            }
            sort = Sort.by(direction, orderField);
        }
        if (!sort.isSorted()) {
            sort = Sort.by(Sort.Direction.ASC, "firstName");
        }
        return PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),sort);
    }

}
