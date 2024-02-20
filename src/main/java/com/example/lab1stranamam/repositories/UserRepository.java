package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findById(Long id);
    boolean existsByUsername(String username);
}
