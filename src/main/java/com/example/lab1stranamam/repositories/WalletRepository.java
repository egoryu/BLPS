package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.entity.WalletEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<WalletEntity, Integer> {
    Optional<WalletEntity> findByUsersByUserId(UsersEntity usersEntity);
}
