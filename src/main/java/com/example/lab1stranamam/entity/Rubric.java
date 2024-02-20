package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Rubric")
@Data
@NoArgsConstructor
public class Rubric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    /*@ManyToMany(mappedBy = "rubrics")
    private List<Record> records = new ArrayList<>();*/

    public Rubric(User user, String name) {
        this.user = user;
        this.name = name;
    }
}
