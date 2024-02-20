package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.Poll;
import com.example.lab1stranamam.entity.Record;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Integer> {
    Poll findPollByRecord(Record record);
}
