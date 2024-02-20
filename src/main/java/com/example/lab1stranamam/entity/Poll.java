package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Poll")
@Data
@NoArgsConstructor
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id", unique = true, nullable = false)
    private Record record;

    @Column(name = "question", nullable = false, length = 256)
    private String question;

    @Column(name = "type", columnDefinition = "int default 0")
    private int type;

    public Poll(Record record, String question, int type) {
        this.record = record;
        this.question = question;
        this.type = type;
    }
}
