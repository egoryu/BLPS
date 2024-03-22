package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.HumanDto;
import com.example.lab1stranamam.dto.response.HumanResponseDto;
import com.example.lab1stranamam.entity.HumanEntity;
import com.example.lab1stranamam.entity.RelationEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.repositories.HumanRepository;
import com.example.lab1stranamam.repositories.RelationRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import com.example.lab1stranamam.service.HumanService;
import com.example.lab1stranamam.ulits.Helper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class HumanController {
    private final UsersRepository usersRepository;
    private final HumanRepository humanRepository;
    private final RelationRepository relationRepository;
    private final HumanService humanService;

    public HumanController(UsersRepository usersRepository, HumanRepository humanRepository, RelationRepository relationRepository, HumanService humanService) {
        this.usersRepository = usersRepository;
        this.humanRepository = humanRepository;
        this.relationRepository = relationRepository;
        this.humanService = humanService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInformation(@RequestBody HumanDto humanDto) {
        try {
            String username = humanDto.getUsername();

            if (!usersRepository.existsByUsername(username)) {
                throw new Exception("User with username " + username + " not found");
            }

            HumanEntity humanEntity = humanService.humanSave(humanDto);

            if (humanDto.getExistPerson() != null && humanDto.getNewPerson() != null) {
                throw new Exception("User with two person can`t exist!");
            }

            if (humanDto.getExistPerson() != null) {
                Optional<HumanEntity> person = humanRepository.findById(humanDto.getExistPerson());

                if (person.isEmpty()) {
                    throw new Exception("Human with id " + humanDto.getExistPerson() + " not found");
                }

                relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, person.get(), 0), humanEntity, person.get()));
            }

            if (humanDto.getNewPerson() != null) {
                HumanEntity person = humanService.humanSave(humanDto.getNewPerson());

                relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, person, 0), humanEntity, person));
            }

            for (int cur: humanDto.getExistChildren()) {
                Optional<HumanEntity> child = humanRepository.findById(cur);

                if (child.isEmpty()) {
                    throw new Exception("Child with id " + humanDto.getExistPerson() + " not found");
                }

                relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, child.get(), 1), humanEntity, child.get()));
            }

            for (HumanDto cur: humanDto.getNewChildren()) {
                HumanEntity child = humanService.humanSave(cur);

                relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, child, 1), humanEntity, child));
            }
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok("success");
    }

    /*@PostMapping("/edit")
    public ResponseEntity<?> updateInformation(@RequestBody UserDto userDto) {

    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> getHuman(@PathVariable int id) {
        try {
            Optional<HumanEntity> human = humanRepository.findById(id);

            if (human.isEmpty()) {
                throw new Exception("Human with id " + id + " not found");
            }

            return ResponseEntity.ok(new HumanResponseDto(human.get()));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
