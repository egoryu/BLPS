package com.example.lab1stranamam.service;

import com.example.lab1stranamam.dto.request.MessageDto;
import com.example.lab1stranamam.dto.response.MessageResponseDto;
import com.example.lab1stranamam.entity.ContractEntity;
import com.example.lab1stranamam.entity.HistoryEntity;
import com.example.lab1stranamam.entity.MessageEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.repositories.ContractRepository;
import com.example.lab1stranamam.repositories.HistoryRepository;
import com.example.lab1stranamam.repositories.MessageRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@EnableJms
@Slf4j
public class ContractService {


    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private final ContractRepository contractRepository;
    private final HistoryRepository historyRepository;
    private final JmsTemplate template;

    @Transactional(rollbackFor = {Exception.class})
    public void sentMessage(MessageDto messageDto) throws Exception {
        Optional<UsersEntity> usersEntityFrom = usersRepository.findByUsername(messageDto.getUsernameFrom());
        Optional<UsersEntity> usersEntityTo = usersRepository.findByUsername(messageDto.getUsernameTo());

        if (usersEntityFrom.isEmpty()) {
            throw new Exception("User with username " + messageDto.getUsernameFrom() + " not found");
        }

        if (usersEntityTo.isEmpty()) {
            throw new Exception("User with username " + messageDto.getUsernameTo() + " not found");
        }

        template.convertAndSend("messageQueue",
                new ObjectMapper().writeValueAsString(
                        new MessageResponseDto(
                                messageDto.getMessageId(),
                                " ",
                                messageDto.getDate().toString(),
                                messageDto.getMessageText(),
                                messageDto.getType(),
                                messageDto.getUsernameFrom(),
                                messageDto.getUsernameTo()
                        )
                ));
    }

    @JmsListener(destination = "messageQueue")
    @Transactional
    public void messageListener(String message) {
        try {
            MessageResponseDto messageDto = new ObjectMapper().readValue(message, MessageResponseDto.class);
            Optional<UsersEntity> usersEntityFrom = usersRepository.findByUsername(messageDto.getUsernameFrom());
            Optional<UsersEntity> usersEntityTo = usersRepository.findByUsername(messageDto.getUsernameTo());

            MessageEntity messageEntity = new MessageEntity(LocalDateTime.parse(messageDto.getDate()), messageDto.getMessageText(),
                    messageDto.getType(), usersEntityFrom.get(), usersEntityTo.get());
            messageRepository.save(messageEntity);

            template.convertAndSend("historyQueue", new ObjectMapper().writeValueAsString(
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
            log.info("Que1: " + messageEntity.getMessageText() + " " + LocalDateTime.now());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @JmsListener(destination = "deleteQueue")
    @Transactional
    public void deleteListener(String message) {
        try {
            MessageResponseDto messageResponseDto = new ObjectMapper().readValue(message, MessageResponseDto.class);
            Optional<MessageEntity> messageEntity = messageRepository.findById(messageResponseDto.getMessageId());

            if (messageEntity.isPresent()) {
                messageRepository.delete(messageEntity.get());

                template.convertAndSend("historyQueue", new ObjectMapper().writeValueAsString(
                        new MessageResponseDto(
                                messageEntity.get().getId(),
                                LocalDateTime.now().toString(),
                                messageEntity.get().getDate().toString(),
                                messageEntity.get().getMessageText(),
                                4,
                                messageEntity.get().getUsersByFrom().getUsername(),
                                messageEntity.get().getUsersByTo().getUsername()
                        )
                ));
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @JmsListener(destination = "historyQueue")
    @Transactional
    public void historyListener(String message) {
        try {
            MessageResponseDto messageResponseDto = new ObjectMapper().readValue(message, MessageResponseDto.class);
            historyRepository.save(
                    new HistoryEntity(
                            messageResponseDto.getMessageId(),
                            messageResponseDto.getUsernameFrom(),
                            messageResponseDto.getUsernameTo(),
                            LocalDateTime.parse(messageResponseDto.getDate()),
                            messageResponseDto.getMessageText(),
                            messageResponseDto.getType(),
                            LocalDateTime.parse(messageResponseDto.getDeleteTime()))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public void editMessage(MessageDto messageDto) throws Exception {
        Optional<MessageEntity> messageEntityOptional = messageRepository.findById(messageDto.getMessageId());

        if (messageEntityOptional.isEmpty()) {
            throw new Exception("Message with id " + messageDto.getMessageId() + " not found");
        }

        MessageEntity message = messageEntityOptional.get();
        message.setMessageText(messageDto.getMessageText());
        message.setType(messageDto.getType());

        messageRepository.save(message);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void acceptContract(MessageDto messageDto) throws Exception {
        Optional<UsersEntity> usersEntityFrom = usersRepository.findByUsername(messageDto.getUsernameFrom());
        Optional<UsersEntity> usersEntityTo = usersRepository.findByUsername(messageDto.getUsernameTo());
        if (usersEntityFrom.isEmpty()) {
            throw new Exception("User with username " + messageDto.getUsernameFrom() + " not found");
        }

        if (usersEntityTo.isEmpty()) {
            throw new Exception("User with username " + messageDto.getUsernameTo() + " not found");
        }

        ContractEntity contract = new ContractEntity(messageDto.getDate(),
                messageDto.getType(), usersEntityFrom.get(), usersEntityTo.get());
        contractRepository.save(contract);
    }
}
