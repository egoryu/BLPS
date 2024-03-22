package com.example.lab1stranamam.dto.response;

import com.example.lab1stranamam.dto.request.HumanDto;
import com.example.lab1stranamam.entity.HumanEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HumanResponseDto {
    private int userId;
    private String name;
    private String surname;
    private int age;
    private int sex;
    private String status;
    private String address;
    private String phone;
    private String description;
    private List<RelationResponseDto> relations;

    public HumanResponseDto(HumanEntity humanEntity) {
        this.userId = humanEntity.getUserId().getId();
        this.name = humanEntity.getName();
        this.surname = humanEntity.getSurname();
        this.age = humanEntity.getAge();
        this.sex = humanEntity.getSex();
        this.status = humanEntity.getStatus();
        this.address = humanEntity.getAddress();
        this.phone = humanEntity.getPhone();
        this.description = humanEntity.getDescription();

        this.relations = humanEntity.getRelationsById().stream().map(val -> {
            if (val.getHumanByFirstPerson().getId() == this.userId) {
                return new RelationResponseDto(val.getHumanBySecondPerson().getId(), val.getRelation());
            }
            return new RelationResponseDto(val.getHumanByFirstPerson().getId(), val.getRelation());
        }).toList();
    }
}
