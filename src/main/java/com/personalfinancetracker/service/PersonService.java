package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.model.SearchCriteria;
import com.personalfinancetracker.jpa.Person;
import com.personalfinancetracker.mapper.PersonMapper;
import com.personalfinancetracker.model.GenericSpecification;
import com.personalfinancetracker.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public void processPerson(PersonDto personDto) {
        personRepository.findById(personDto.getPersonId())
                .ifPresentOrElse(
                        person -> updatePerson(person, personDto),
                        () -> createPerson(personDto)
                );
    }

    public List<PersonDto> searchByCriteria(List<String> keys, List<String> operations, List<String> values, int page,
                                            int size, String orderField, String orderDirection) {
        Pageable pageable = PageRequest.of(page, size, getSort(orderField, orderDirection));
        // If no filters are provided, return all records
        if (keys == null || operations == null || values == null || keys.isEmpty()) {
            return getAllPersons(pageable);
        }

        if (keys.size() != operations.size() || keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys, operations, and values must have the same size");
        }

        validateCriteria(keys, operations, values);
        List<SearchCriteria> criteriaList = buildSearchCriteria(keys, operations, values);

        return search(criteriaList, pageable);
    }

    public List<PersonDto> getAllPersons(Pageable pageable){
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

    private void updatePerson(Person person, PersonDto personDto) {
        personMapper.updatePerson(person, personDto);
        personRepository.save(person);
    }

    /**
     * Validates search criteria lists have the same size.
     */
    private void validateCriteria(List<String> keys, List<String> operations, List<String> values) {
        if (keys.size() != operations.size() || keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys, operations, and values must have the same size.");
        }
    }

    /**
     * Converts lists of search parameters into a list of SearchCriteria objects.
     */
    private List<SearchCriteria> buildSearchCriteria(List<String> keys, List<String> operations, List<String> values) {
        return IntStream.range(0, keys.size())
                .mapToObj(i -> new SearchCriteria(keys.get(i), operations.get(i), values.get(i)))
                .collect(Collectors.toList());
    }

}
