package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.UserDto;
import com.example.lab1stranamam.entity.Record;
import com.example.lab1stranamam.entity.User;
import com.example.lab1stranamam.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        Map<Object, Object> response = new HashMap<>();
        String username = userDto.getUsername();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            response.put("error", "User with username " + username + " not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (!user.getPassword().equals(userDto.getPassword())) {
            response.put("error", "Wrong password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        Map<Object, Object> response = new HashMap<>();
        String username = userDto.getUsername();

        if (userRepository.existsByUsername(userDto.getUsername())) {
            response.put("error", "User with username " + username + " exist");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        User user = new User(userDto.getUsername(), userDto.getEmail(), userDto.getPassword());
        userRepository.save(user);

        return ResponseEntity.ok("success");
    }
}
