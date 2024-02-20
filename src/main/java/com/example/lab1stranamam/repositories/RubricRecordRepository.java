package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.Record;
import com.example.lab1stranamam.entity.Rubric;
import com.example.lab1stranamam.entity.RubricRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RubricRecordRepository extends CrudRepository<RubricRecord, Integer> {
    List<RubricRecord> findRubricRecordsByRecord(Record record);
}
