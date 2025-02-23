package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.dto.SearchCriteria;
import com.personalfinancetracker.jpa.Person;
import com.personalfinancetracker.mapper.PersonMapper;
import com.personalfinancetracker.model.GenericSpecification;
import com.personalfinancetracker.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public void processPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getPersonId())) {
            updatePerson(personDto, personDto.getPersonId());
        } else {
            createPerson(personDto);
        }
    }

    public List<PersonDto> searchByCriteria(List<String> keys, List<String> operations, List<String> values, int page, int size, String orderField, String orderDirection) {
        Pageable pageable = PageRequest.of(page, size, getSort(orderField, orderDirection));
        // If no filters are provided, return all records
        if (keys == null || operations == null || values == null) {
            return getAllPerson(pageable);
        }

        if (keys.size() != operations.size() || keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys, operations, and values must have the same size");
        }

        List<SearchCriteria> criteriaList = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            criteriaList.add(new SearchCriteria(keys.get(i), operations.get(i), values.get(i)));
        }

        return search(criteriaList, pageable);  // Calls the existing search method
    }

    public List<PersonDto> getAllPerson(Pageable pageable){
        return personRepository.findAll(pageable)
                .stream()
                .map(personMapper::personToPersonDto)
                .collect(Collectors.toList());
    }

    private List<PersonDto> search(List<SearchCriteria> criteriaList, Pageable pageable) {
        Specification<Person> specification = Specification.where(null);

        for (SearchCriteria criteria : criteriaList) {
            specification = specification.and(new GenericSpecification<>(criteria));
        }
        return personRepository.findAll(specification, pageable)
                .stream().map(personMapper::personToPersonDto)
                .collect(Collectors.toList());
    }

    private Sort getSort(String orderField, String orderDirection) {
        if (orderField != null && !orderField.isEmpty()) {
            return "desc".equalsIgnoreCase(orderDirection) ? Sort.by(orderField).descending() : Sort.by(orderField).ascending();
        }
        return Sort.unsorted();
    }

    private void createPerson(PersonDto personDto) {
        personRepository.save(personMapper.personDtoToPerson(personDto));
    }

    private void updatePerson(PersonDto personDto, UUID requestedId) {
        Person person = personRepository.findById(requestedId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + requestedId));

        personMapper.updatePerson(person, personDto);
        personRepository.save(person);
    }

}
