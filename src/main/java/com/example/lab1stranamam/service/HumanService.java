package com.example.lab1stranamam.service;

import com.example.lab1stranamam.dto.request.HumanDto;
import com.example.lab1stranamam.entity.HumanEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.repositories.HumanRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HumanService {
    private final HumanRepository humanRepository;
    private final UsersRepository usersRepository;

    public HumanService(HumanRepository humanRepository, UsersRepository usersRepository) {
        this.humanRepository = humanRepository;
        this.usersRepository = usersRepository;
    }

    public HumanEntity humanSave(HumanDto humanDto) throws Exception {
        String username = humanDto.getUsername();
        Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (humanRepository.existsByUserId(user.get())) {
            throw new Exception("Record with username " + username + " exist");
        }

        HumanEntity humanEntity = new HumanEntity(humanDto.getName(), humanDto.getSurname(), humanDto.getAge(),
                humanDto.getSex(), humanDto.getStatus(), humanDto.getAddress(), humanDto.getPhone(),
                humanDto.getDescription(), user.get());

        humanRepository.save(humanEntity);

        return humanEntity;
    }
}
