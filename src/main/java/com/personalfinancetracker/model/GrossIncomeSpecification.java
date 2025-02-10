package com.personalfinancetracker.model;

import com.personalfinancetracker.dto.GrossIncomeDto;
import com.personalfinancetracker.jpa.GrossIncome;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GrossIncomeSpecification {
    public static Specification<GrossIncome> search(GrossIncomeDto request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request != null) {
                if (Objects.nonNull(request.getGrossIncomeValue())) {
                    predicates.add(criteriaBuilder.equal(root.get("grossIncomeValue"), request.getGrossIncomeValue()));
                }
                if (Objects.nonNull(request.getPersonId())) {
                    predicates.add(criteriaBuilder.equal(root.get("personId"), request.getPersonId()));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
