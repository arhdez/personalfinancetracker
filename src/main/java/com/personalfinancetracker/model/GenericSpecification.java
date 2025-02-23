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
        Path<?> path = getPath(root, criteria.getKey());
        Object value = convertValue(path.getJavaType(), criteria.getValue());
        return createPredicate(builder, path, value, criteria.getOperation());
    }

    private Path<?> getPath(Root<T> root, String fieldName) {
        try {
            return root.get(fieldName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName, e);
        }
    }

    private Object convertValue(Class<?> type, String value) {
        return switch (type.getSimpleName()) {
            case "UUID" -> UUID.fromString(value);
            case "Double" -> Double.valueOf(value);
            case "Integer" -> Integer.valueOf(value);
            default -> value; // Default to String
        };
    }

    @SuppressWarnings("unchecked")
    private Predicate createPredicate(CriteriaBuilder builder, Path<?> path, Object value, String operation) {
        if (!(value instanceof Comparable<?>)) {
            throw new IllegalArgumentException("Unsupported comparison for field: " + criteria.getKey());
        }

        Expression<Comparable<Object>> comparablePath = (Expression<Comparable<Object>>) path;
        Comparable<Object> comparableValue = (Comparable<Object>) value;

        return switch (operation) {
            case ">" -> builder.greaterThan(comparablePath, comparableValue);
            case "<" -> builder.lessThan(comparablePath, comparableValue);
            case "=" -> builder.equal(path, value);
            case "like" -> createLikePredicate(builder, path, value);
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }

    private Predicate createLikePredicate(CriteriaBuilder builder, Path<?> path, Object value) {
        if (path.getJavaType() == String.class) {
            return builder.like(builder.lower(path.as(String.class)), "%%" + value.toString().toLowerCase() + "%%");
        }
        throw new IllegalArgumentException("LIKE operation is only supported for String fields.");
    }
}
