package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Rubric_Record")
@Data
@NoArgsConstructor
public class RubricRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rubric_id", referencedColumnName = "id", nullable = false)
    private Rubric rubric;

    @ManyToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false)
    private Record record;

    public RubricRecord(Rubric rubric, Record record) {
        this.rubric = rubric;
        this.record = record;
    }
}
