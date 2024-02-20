package com.example.lab1stranamam.dto;

import com.example.lab1stranamam.entity.Answer;
import com.example.lab1stranamam.entity.Rubric;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ResponseRecordDto {
    private Long userId;
    private String header;
    private String body;
    private Integer recordType;
    private Integer rating;
    private LocalDateTime time;
    List<ResponseRubricDto> rubrics;
    private Long pollId;
    private String question;
    private Integer pollType;
    List<AnswerDto> answers;
}
