package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.Record;
import com.example.lab1stranamam.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Integer> {
    List<Record> getRecordsByUser(User user);
    List<Record> findAllByOrderByCreateTimeDesc();
}
