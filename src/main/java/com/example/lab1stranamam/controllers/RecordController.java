package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.RecordDto;
import com.example.lab1stranamam.dto.ResponseRecordDto;
import com.example.lab1stranamam.dto.RubricDto;
import com.example.lab1stranamam.entity.*;
import com.example.lab1stranamam.entity.Record;
import com.example.lab1stranamam.repositories.*;
import com.example.lab1stranamam.service.RecordService;
import com.example.lab1stranamam.ulits.Helper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/diary")
public class RecordController {
    private final UserRepository userRepository;
    private final RecordService recordService;
    private final RecordRepository recordRepository;
    private final RubricRepository rubricRepository;

    public RecordController(UserRepository userRepository, RecordService recordService, RecordRepository recordRepository, RubricRepository rubricRepository) {
        this.userRepository = userRepository;
        this.recordService = recordService;
        this.recordRepository = recordRepository;
        this.rubricRepository = rubricRepository;
    }

    @PostMapping("/edit")
    public ResponseEntity<?> saveRecord(@RequestBody RecordDto recordDto) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(recordDto.getUserId()));
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!recordService.saveRecord(userOptional.get(), recordDto)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("success");
    }

    @PostMapping("/add-rubric")
    public ResponseEntity<?> addNewRubric(@RequestBody RubricDto rubricDto) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(rubricDto.getUserId()));
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        Rubric rubric = new Rubric(user, rubricDto.getName());
        rubricRepository.save(rubric);


        return ResponseEntity.ok("success");
    }

    @GetMapping("/records")
    public ResponseEntity<?> getAllRecords() {
        List<ResponseRecordDto> records = recordService.getRecords(null);
        Map<String, Object> response = new HashMap<>();
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recordsGj/sort_by/{sortBy}/sort_dir/{sortOrder}")
    public ResponseEntity<?> getAllRecordsWithArgs(@PathVariable String sortBy, @PathVariable String sortOrder) {
        List<ResponseRecordDto> records = recordService.getRecords(null);
        List<ResponseRecordDto> sortedRecords = Helper.sortRecords(records, sortBy, sortOrder);
        Map<String, Object> response = new HashMap<>();
        response.put("data", sortedRecords);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-diary/{id}")
    public ResponseEntity<?> getRecords(@PathVariable Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<ResponseRecordDto> records = recordService.getRecords(user);
        Map<String, Object> response = new HashMap<>();
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-diary/{id}/sort_by/{sortBy}/sort_dir/{sortOrder}")
    public ResponseEntity<?> getRecordsWithArgs(@PathVariable Long id, @PathVariable String sortBy, @PathVariable String sortOrder) {
        User user = userRepository.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<ResponseRecordDto> records = recordService.getRecords(user);
        List<ResponseRecordDto> sortedRecords = Helper.sortRecords(records, sortBy, sortOrder);
        Map<String, Object> response = new HashMap<>();
        response.put("data", sortedRecords);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rubrics/{id}")
    public ResponseEntity<?> getRubrics(@PathVariable Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Rubric> rubrics = rubricRepository.findRubricsByUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("data", rubrics);

        return ResponseEntity.ok(response);
    }
}
