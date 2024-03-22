package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.UserDto;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsersRepository usersRepository;
    public AuthController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            String username = userDto.getUsername();
            Optional<UsersEntity> user = usersRepository.findByUsername(username);

            if (user.isEmpty()) {
                throw new Exception("User with username " + username + " not found");
            }

            if (!user.get().getPassword().equals(userDto.getPassword())) {
                throw new Exception("Wrong password");
            }

            return ResponseEntity.ok(user.get().getId());
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            String username = userDto.getUsername();

            if (usersRepository.existsByUsername(userDto.getUsername())) {
                throw new Exception("User with username " + username + " exist");
            }

            UsersEntity user = new UsersEntity(userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), userDto.getRole());
            usersRepository.save(user);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok("success");
    }
}
