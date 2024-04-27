package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.HumanDto;
import com.example.lab1stranamam.dto.response.HumanResponseDto;
import com.example.lab1stranamam.entity.HumanEntity;
import com.example.lab1stranamam.entity.RelationEntity;
import com.example.lab1stranamam.repositories.HumanRepository;
import com.example.lab1stranamam.repositories.RelationRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import com.example.lab1stranamam.service.HumanService;
import com.example.lab1stranamam.ulits.Helper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@AllArgsConstructor
public class HumanController {
    private final HumanService humanService;

    @PostMapping("/")
    public ResponseEntity<?> addInformation(@RequestBody HumanDto humanDto) {
        try {
            humanService.addHumanInformation(humanDto);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHuman(@PathVariable int id) {
        try {
            return ResponseEntity.ok(new HumanResponseDto(humanService.getHumanInformation(id)));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHuman(@PathVariable int id) {
        try {
            humanService.deleteHumanInformation(id);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
