package com.personalfinancetracker.repository;

import com.personalfinancetracker.jpa.Spend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface SpendRepository extends CrudRepository<Spend, UUID>, PagingAndSortingRepository<Spend, UUID>,
        JpaRepository<Spend, UUID>, JpaSpecificationExecutor<Spend> {
   // boolean existsById(UUID spendId);
    boolean existsBySpendAmountAndDateSpendAndPersonIdAndCategoryIdAndSpendDescription(Double spendAmount, LocalDate dateSpend, UUID personId, UUID categoryId, String spendDescription);
}
