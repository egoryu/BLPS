package com.example.lab1stranamam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerDto {
    private Long answerId;
    private int index;
    private String answerText;
}
