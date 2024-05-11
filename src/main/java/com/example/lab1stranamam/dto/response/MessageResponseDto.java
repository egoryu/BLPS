package com.example.lab1stranamam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageResponseDto {
    private int messageId;
    private String deleteTime;
    private String date;
    private String messageText;
    private int type;
    private String usernameFrom;
    private String usernameTo;
}
