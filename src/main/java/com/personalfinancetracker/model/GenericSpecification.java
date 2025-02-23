package com.personalfinancetracker.model;

import com.personalfinancetracker.dto.SearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class GenericSpecification<T> implements Specification<T> {
    private final SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // Ensure the field exists in the entity
        Path<?> path;
        try {
            path = root.get(criteria.getKey());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid field name: " + criteria.getKey(), e);
        }

        // Convert value to the appropriate type
        Object value = convertValue(path.getJavaType(), criteria.getValue());

        // Ensure the value type matches the operation
        if (value instanceof Comparable) {
            return switch (criteria.getOperation()) {
                case ">" -> builder.greaterThan((Expression<Comparable>) path, (Comparable) value);
                case "<" -> builder.lessThan((Expression<Comparable>) path, (Comparable) value);
                case "=" -> builder.equal(path, value);
                case "like" -> {
                    if (path.getJavaType() == String.class) {
                        yield builder.like(path.as(String.class), "%" + value + "%");
                    } else {
                        throw new IllegalArgumentException("LIKE operation is only supported for String fields.");
                    }
                }
                default -> throw new IllegalArgumentException("Invalid operation: " + criteria.getOperation());
            };
        } else {
            throw new IllegalArgumentException("Unsupported comparison for field: " + criteria.getKey());
        }
    }

    private Object convertValue(Class<?> type, String value) {
        if (type == UUID.class) return UUID.fromString(value);
        if (type == Double.class) return Double.valueOf(value);
        if (type == Integer.class) return Integer.valueOf(value);
        return value; // Default to String
    }
}
