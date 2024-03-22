package com.example.lab1stranamam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private String role;
}
