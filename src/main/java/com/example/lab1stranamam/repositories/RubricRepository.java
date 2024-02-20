package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.Rubric;
import com.example.lab1stranamam.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RubricRepository extends CrudRepository<Rubric, Integer> {
    Rubric findById(Long id);
    List<Rubric> findRubricsByUser(User user);
}
