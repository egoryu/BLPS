package com.example.lab1stranamam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageRab {
    private int messageId;
    private LocalDateTime date;
    private String messageText;
    private int type;
    private String usernameFrom;
    private String usernameTo;
}
