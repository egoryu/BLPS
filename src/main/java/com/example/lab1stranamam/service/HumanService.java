package com.example.lab1stranamam.service;

import com.example.lab1stranamam.dto.request.HumanDto;
import com.example.lab1stranamam.entity.HumanEntity;
import com.example.lab1stranamam.entity.RelationEntity;
import com.example.lab1stranamam.entity.UsersEntity;
import com.example.lab1stranamam.repositories.HumanRepository;
import com.example.lab1stranamam.repositories.RelationRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import com.example.lab1stranamam.ulits.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HumanService {
    private final UsersRepository usersRepository;
    private final HumanRepository humanRepository;
    private final RelationRepository relationRepository;
    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.REPEATABLE_READ, timeout = 120)
    public void addHumanInformation(HumanDto humanDto) throws Exception {
        String username = humanDto.getUsername();

        if (!usersRepository.existsByUsername(username)) {
            throw new Exception("User with username " + username + " not found");
        }

        HumanEntity humanEntity = saveHumanInformation(humanDto);

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
            HumanEntity person = saveHumanInformation(humanDto.getNewPerson());

            relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, person, 0), humanEntity, person));
        }

        for (int cur: humanDto.getExistChildren()) {
            Optional<HumanEntity> child = humanRepository.findById(cur);

            if (child.isEmpty()) {
                throw new Exception("Child with id " + cur + " not found");
            }

            relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, child.get(), 1), humanEntity, child.get()));
        }

        for (HumanDto cur: humanDto.getNewChildren()) {
            HumanEntity child = saveHumanInformation(cur);

            relationRepository.save(new RelationEntity(Helper.getRelation(humanEntity, child, 1), humanEntity, child));
        }
    }

    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.MANDATORY, isolation = Isolation.REPEATABLE_READ)
    public HumanEntity saveHumanInformation(HumanDto humanDto) throws Exception {
        String username = humanDto.getUsername();
        Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new Exception("User with username " + username + " not found");
        }

        if (humanRepository.existsByUserId(user.get())) {
            throw new Exception("Record with username " + username + " exist");
        }

        HumanEntity humanEntity = new HumanEntity(humanDto.getName(), humanDto.getSurname(), humanDto.getAge(),
                humanDto.getSex(), humanDto.getStatus(), humanDto.getAddress(), humanDto.getPhone(),
                humanDto.getDescription(), user.get());

        humanRepository.save(humanEntity);


        return humanEntity;
    }

    @Transactional(rollbackFor = {Exception.class})
    public HumanEntity getHumanInformation(int id) throws Exception {
        Optional<HumanEntity> human = humanRepository.findById(id);

        if (human.isEmpty()) {
            throw new Exception("Human with id " + id + " not found");
        }

        return human.get();
    }

    @Transactional(rollbackFor = {Exception.class}, isolation = Isolation.SERIALIZABLE)
    public void deleteHumanInformation(int id) throws Exception {
        Optional<HumanEntity> human = humanRepository.findById(id);

        if (human.isEmpty()) {
            throw new Exception("Human with id " + id + " not found");
        }

        humanRepository.delete(human.get());
    }
}
