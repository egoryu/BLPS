package com.example.lab1stranamam.service;

import com.example.lab1stranamam.dto.*;
import com.example.lab1stranamam.entity.*;
import com.example.lab1stranamam.entity.Record;
import com.example.lab1stranamam.repositories.*;
import com.example.lab1stranamam.ulits.Helper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RubricRepository rubricRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final RecordRepository recordRepository;
    private final PollRepository pollRepository;
    private final RubricRecordRepository rubricRecordRepository;

    public RecordService(RubricRepository rubricRepository, UserRepository userRepository, AnswerRepository answerRepository, RecordRepository recordRepository, PollRepository pollRepository, RubricRecordRepository rubricRecordRepository) {
        this.rubricRepository = rubricRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.recordRepository = recordRepository;
        this.pollRepository = pollRepository;
        this.rubricRecordRepository = rubricRecordRepository;
    }

    public boolean saveRecord(User user, RecordDto recordDto) {
        Record record = new Record(user, recordDto.getHeader(), recordDto.getBody(), recordDto.getTime(), recordDto.getRating(), recordDto.getRecordType());
        Poll poll = new Poll(record, recordDto.getQuestion(), recordDto.getPollType());
        List<String> answers = recordDto.getAnswers();
        List<Long> rubrics = recordDto.getRubrics();

        recordRepository.save(record);
        pollRepository.save(poll);

        if (answers != null) {
            for (int i = 0; i < answers.size(); i++) {
                answerRepository.save(new Answer(poll, answers.get(i), i));
            }
        }

        if (rubrics != null) {
            for (Long rubric : rubrics) {
                rubricRecordRepository.save(new RubricRecord(rubricRepository.findById(rubric), record));
            }
        }

        return true;
    }

    public List<ResponseRecordDto> getRecords(User user) {
        List<Record> records;
        List<ResponseRecordDto> recordDtos = new ArrayList<>();

        if (user == null) {
            records = recordRepository.findAllByOrderByCreateTimeDesc();
        } else {
            records = recordRepository.getRecordsByUser(user);
        }

        for (Record record: records) {
            Poll poll = pollRepository.findPollByRecord(record);
            List<AnswerDto> answers = null;
            if (poll != null) {
                answers = answerRepository.findAnswersByPoll(poll).stream().map(val -> new AnswerDto(val.getId(), val.getIndex(), val.getAnswerText())).toList();
            }
            List<ResponseRubricDto> rubrics = rubricRecordRepository.findRubricRecordsByRecord(record).stream()
                    .map(RubricRecord::getRubric)
                    .map(val -> new ResponseRubricDto(val.getId(), val.getName()))
                    .toList();

            recordDtos.add(new ResponseRecordDto(record.getUser().getId(), record.getHeader(), record.getBody(), record.getType(),
                    record.getType(), record.getCreateTime(), rubrics, poll != null ? poll.getId() : null, poll != null ? poll.getQuestion() : null,
                    poll != null ? poll.getType() : 0, answers));
        }

        return recordDtos;
    }
}
