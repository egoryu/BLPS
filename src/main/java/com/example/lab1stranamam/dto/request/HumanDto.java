package com.example.lab1stranamam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HumanDto {
    private String username;
    private String name;
    private String surname;
    private int age;
    private int sex;
    private String status;
    private String address;
    private String phone;
    private String description;
    private List<HumanDto> newChildren;
    private List<Integer> existChildren;
    private HumanDto newPerson;
    private Integer existPerson;
}
