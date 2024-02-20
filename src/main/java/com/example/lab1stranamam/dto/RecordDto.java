package com.example.lab1stranamam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RecordDto {
    private Long userId;
    private String header;
    private String body;
    private Integer recordType;
    private Integer rating;
    private LocalDateTime time;
    List<Long> rubrics;
    private String question;
    private Integer pollType;
    List<String> answers;
}
