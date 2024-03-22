package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.HumanEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanRepository extends CrudRepository<HumanEntity, Integer> {
    Boolean existsByUserId(UsersEntity userId);
    HumanEntity findByUserId(UsersEntity usersEntity);
}
