package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

    List<UsersEntity> findAllByRole(String role);
}
