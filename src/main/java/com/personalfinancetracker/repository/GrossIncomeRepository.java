package com.personalfinancetracker.repository;

import com.personalfinancetracker.jpa.GrossIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GrossIncomeRepository extends CrudRepository<GrossIncome, UUID>, PagingAndSortingRepository<GrossIncome, UUID>,
        JpaRepository<GrossIncome, UUID>, JpaSpecificationExecutor<GrossIncome> {
    boolean existsByPersonId(UUID personId);
    Optional<GrossIncome> findByPersonId(UUID personId);
}
