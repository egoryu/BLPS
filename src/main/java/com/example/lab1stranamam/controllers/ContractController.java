package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.MessageDto;
import com.example.lab1stranamam.dto.request.WalletDto;
import com.example.lab1stranamam.entity.ContractEntity;
import com.example.lab1stranamam.entity.MessageEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.entity.WalletEntity;
import com.example.lab1stranamam.repositories.ContractRepository;
import com.example.lab1stranamam.repositories.MessageRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/contract")
public class ContractController {
    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;
    private final ContractRepository contractRepository;

    public ContractController(UsersRepository usersRepository, MessageRepository messageRepository, ContractRepository contractRepository) {
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
        this.contractRepository = contractRepository;
    }

    @PostMapping("/send_invite")
    public ResponseEntity<?> sendInvite(@RequestBody MessageDto messageDto) {
        try {
            if (!usersRepository.existsByUsername(messageDto.getUsernameFrom())) {
                throw new Exception("User with username " + messageDto.getUsernameFrom() + " not found");
            }

            if (!usersRepository.existsByUsername(messageDto.getUsernameTo())) {
                throw new Exception("User with username " + messageDto.getUsernameTo() + " not found");
            }

            MessageEntity message = new MessageEntity(messageDto.getDate(), messageDto.getMessageText(),
                    messageDto.getType(), usersRepository.findByUsername(messageDto.getUsernameFrom()).get(),
                    usersRepository.findByUsername(messageDto.getUsernameTo()).get());
            messageRepository.save(message);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @PutMapping("/send_invite")
    public ResponseEntity<?> updateInvite(@RequestBody MessageDto messageDto) {
        try {
            Optional<MessageEntity> messageEntityOptional = messageRepository.findById(messageDto.getMessageId());

            if (messageEntityOptional.isEmpty()) {
                throw new Exception("Message with id " + messageDto.getMessageId() + " not found");
            }

            MessageEntity message = messageEntityOptional.get();
            message.setMessageText(messageDto.getMessageText());
            message.setType(messageDto.getType());

            messageRepository.save(message);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("/accept_contract")
    public ResponseEntity<?> acceptContract(@RequestBody MessageDto messageDto) {
        try {
            if (!usersRepository.existsByUsername(messageDto.getUsernameFrom())) {
                throw new Exception("User with username " + messageDto.getUsernameFrom() + " not found");
            }

            if (!usersRepository.existsByUsername(messageDto.getUsernameTo())) {
                throw new Exception("User with username " + messageDto.getUsernameTo() + " not found");
            }

            ContractEntity contract = new ContractEntity(messageDto.getDate(),
                    messageDto.getType(), usersRepository.findByUsername(messageDto.getUsernameFrom()).get(),
                    usersRepository.findByUsername(messageDto.getUsernameTo()).get());
            contractRepository.save(contract);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    /*@GetMapping("/{userId}")
    public ResponseEntity<?> getHuman(@PathVariable int userId) {
        try {
            Optional<UsersEntity> user = usersRepository.findById(userId);

            if (user.isEmpty()) {
                throw new Exception("User with id " + userId + " not found");
            }
            List<ContractEntity> contracts = contractRepository.findAllByUsersByMaster(user.get());

            return ResponseEntity.ok(new WalletDto(wallet.get()));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }*/
}
