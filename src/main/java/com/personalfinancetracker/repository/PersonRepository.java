package com.personalfinancetracker.repository;

import com.personalfinancetracker.jpa.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID>, PagingAndSortingRepository<Person, UUID>,
        JpaRepository<Person, UUID>, JpaSpecificationExecutor<Person> {
}
