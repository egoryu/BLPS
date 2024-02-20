package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.Answer;
import com.example.lab1stranamam.entity.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findAnswersByPoll(Poll poll);
}
