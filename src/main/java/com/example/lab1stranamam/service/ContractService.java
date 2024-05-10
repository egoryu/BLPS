package com.example.lab1stranamam.service;

import com.example.lab1stranamam.dto.request.MessageDto;
import com.example.lab1stranamam.entity.ContractEntity;
import com.example.lab1stranamam.entity.MessageEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.repositories.ContractRepository;
import com.example.lab1stranamam.repositories.MessageRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@EnableRabbit
public class ContractService {


    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private final ContractRepository contractRepository;
    private RabbitTemplate template;

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

        template.convertAndSend("testExchange", "kekKey", "keker");
        System.out.println("kek");
        /*MessageEntity message = new MessageEntity(messageDto.getDate(), messageDto.getMessageText(),
                messageDto.getType(), usersEntityFrom.get(), usersEntityTo.get());
        messageRepository.save(message);*/
    }

    @RabbitListener(queues = "kekQueue")
    public void processMyQueue(String message) {
        System.out.println("Received from myQueue : " + message);
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
