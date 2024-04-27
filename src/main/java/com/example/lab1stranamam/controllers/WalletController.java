package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.UserDto;
import com.example.lab1stranamam.dto.request.WalletDto;
import com.example.lab1stranamam.dto.response.HumanResponseDto;
import com.example.lab1stranamam.entity.*;
import com.example.lab1stranamam.enums.OrderState;
import com.example.lab1stranamam.enums.Payment;
import com.example.lab1stranamam.repositories.ItemRepository;
import com.example.lab1stranamam.repositories.OrderRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import com.example.lab1stranamam.repositories.WalletRepository;
import com.example.lab1stranamam.security.User;
import com.example.lab1stranamam.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class WalletController {
    private final WalletService walletService;


    @PostMapping("/deposit")
    public ResponseEntity<?> makeDeposit(@RequestBody WalletDto walletDto) {
        try {
            walletService.createWallet(walletDto);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<?> payForOrder(@RequestBody UserDto userDto, @PathVariable int id) {
        try {
            walletService.paymentForOder(userDto, id);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(new WalletDto(walletService.getWalletInformation(userId)));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
