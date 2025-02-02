package com.personalfinancetracker.repository;

import com.personalfinancetracker.jpa.GrossIncome;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GrossIncomeRespository extends CrudRepository<GrossIncome, UUID>, PagingAndSortingRepository<GrossIncome, UUID> {
}
