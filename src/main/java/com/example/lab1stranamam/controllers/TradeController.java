package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.OrderDto;
import com.example.lab1stranamam.dto.request.WalletDto;
import com.example.lab1stranamam.dto.response.HumanResponseDto;
import com.example.lab1stranamam.dto.response.UserResponseDto;
import com.example.lab1stranamam.entity.*;
import com.example.lab1stranamam.enums.OrderState;
import com.example.lab1stranamam.enums.Role;
import com.example.lab1stranamam.repositories.*;
import com.example.lab1stranamam.service.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/trade")
@AllArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @GetMapping("/traders")
    public ResponseEntity<?> findAllTrader(Pageable pageable) {
        try {
            return ResponseEntity.ok(tradeService.getAllUsersByRole(pageable, Role.TRADER));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/traders/{id}")
    public ResponseEntity<?> findTraderContractor(@PathVariable int id, Pageable pageable) {
        try {
            return ResponseEntity.ok(tradeService.getUserContractors(pageable, id, Role.TRADER));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        try {
            tradeService.createOrder(orderDto);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/order/{id}/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WOMAN', 'TRADER')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int id, @PathVariable int status) {
        try {
            tradeService.updateOrder(id, status);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrderItem(@RequestBody WalletDto walletDto, @PathVariable int id) {
        try {
            tradeService.updateOrderItem(walletDto, id);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
