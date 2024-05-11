package com.example.lab1stranamam.service;

import com.example.lab1stranamam.dto.request.MessageDto;
import com.example.lab1stranamam.dto.response.MessageResponseDto;
import com.example.lab1stranamam.entity.MessageEntity;
import com.example.lab1stranamam.repositories.MessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CleanService {
    private final MessageRepository messageRepository;
    private final JmsTemplate template;

    @Transactional
    public void cleanDeleteMessage() {
        List<MessageEntity> messageEntities = messageRepository.findAllByType(3);

        messageEntities.forEach(messageEntity -> {
            try {
                template.convertAndSend("deleteQueue", new ObjectMapper().writeValueAsString(
                        new MessageResponseDto(
                                messageEntity.getId(),
                                LocalDateTime.now().toString(),
                                messageEntity.getDate().toString(),
                                messageEntity.getMessageText(),
                                messageEntity.getType(),
                                messageEntity.getUsersByFrom().getUsername(),
                                messageEntity.getUsersByTo().getUsername()
                        )
                ));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
