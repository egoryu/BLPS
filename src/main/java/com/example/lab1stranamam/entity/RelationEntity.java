package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "relation")
@Data
@NoArgsConstructor
public class RelationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "relation", nullable = false, length = 64)
    private String relation;
    @ManyToOne
    @JoinColumn(name = "first_person", referencedColumnName = "id", nullable = false)
    private HumanEntity humanByFirstPerson;
    @ManyToOne
    @JoinColumn(name = "second_person", referencedColumnName = "id", nullable = false)
    private HumanEntity humanBySecondPerson;

    public RelationEntity(String relation, HumanEntity humanByFirstPerson, HumanEntity humanBySecondPerson) {
        this.relation = relation;
        this.humanByFirstPerson = humanByFirstPerson;
        this.humanBySecondPerson = humanBySecondPerson;
    }
}
