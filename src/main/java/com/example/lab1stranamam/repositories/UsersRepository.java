package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

    Optional<UsersEntity> findById(int id);
    Page<UsersEntity> findAllByRole(String role, Pageable pageable);

    void save(UsersEntity user);
}
