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
import com.example.lab1stranamam.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/contract")
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;
    @PostMapping("/send_invite")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> sendInvite(@RequestBody MessageDto messageDto) {
        try {
            contractService.sentMessage(messageDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @PutMapping("/send_invite")
    @PreAuthorize("hasAnyRole('ADMIN', 'MAN', 'TRADER')")
    public ResponseEntity<?> updateInvite(@RequestBody MessageDto messageDto) {
        try {
            contractService.editMessage(messageDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("/accept_contract")
    @PreAuthorize("hasRole('MAN')")
    public ResponseEntity<?> acceptContract(@RequestBody MessageDto messageDto) {
        try {
            contractService.acceptContract(messageDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }
}
