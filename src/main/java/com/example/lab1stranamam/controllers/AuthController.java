package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.UserDto;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.enums.Role;
import com.example.lab1stranamam.repositories.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;
    public AuthController(UsersRepository usersRepository, PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            String username = userDto.getUsername();
            Optional<UsersEntity> user = usersRepository.findByUsername(username);

            if (user.isEmpty()) {
                throw new Exception("User with username " + username + " not found");
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

            UsersEntity user = new UsersEntity(userDto.getUsername(), userDto.getEmail(), encoder.encode(userDto.getPassword()), userDto.getRole());
            usersRepository.save(user);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok("success");
    }
}
