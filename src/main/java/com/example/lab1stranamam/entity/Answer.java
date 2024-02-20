package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "Answers")
@Data
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "poll_id", referencedColumnName = "id", nullable = false)
    private Poll poll;

    @Column(name = "answer_text", nullable = false, length = 128)
    private String answerText;

    @Column(name = "index", nullable = false)
    private int index;

    public Answer(Poll poll, String answerText, int index) {
        this.poll = poll;
        this.answerText = answerText;
        this.index = index;
    }
}
