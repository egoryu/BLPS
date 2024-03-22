package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.RelationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRepository extends CrudRepository<RelationEntity, Integer> {
}
