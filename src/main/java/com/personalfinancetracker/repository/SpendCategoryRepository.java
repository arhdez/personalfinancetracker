package com.personalfinancetracker.repository;

import com.personalfinancetracker.jpa.SpendCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpendCategoryRepository extends CrudRepository<SpendCategory, UUID>, PagingAndSortingRepository<SpendCategory, UUID> {
}
