package com.personalfinancetracker.repository;

import com.personalfinancetracker.jpa.Spends;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpendsRepository extends CrudRepository<Spends, UUID>, PagingAndSortingRepository<Spends, UUID> {
}
