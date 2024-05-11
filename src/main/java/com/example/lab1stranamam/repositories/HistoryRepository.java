package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, Integer> {
}
