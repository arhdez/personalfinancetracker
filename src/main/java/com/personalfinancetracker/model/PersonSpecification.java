package com.personalfinancetracker.model;

import com.personalfinancetracker.dto.PersonDto;
import com.personalfinancetracker.jpa.Person;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PersonSpecification {
    public static Specification<Person> search(PersonDto request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + request.getFirstName().toLowerCase() + "%"));
            }

            if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + request.getLastName().toLowerCase() + "%"));
            }

            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
            }

            if (request.getDateOfBirth() != null) {
                predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), request.getDateOfBirth()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
