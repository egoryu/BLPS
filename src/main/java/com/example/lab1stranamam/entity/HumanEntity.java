package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "human", schema = "public", catalog = "stranamam")
@Data
@NoArgsConstructor
public class HumanEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Basic
    @Column(name = "surname", nullable = false, length = 32)
    private String surname;
    @Basic
    @Column(name = "age", nullable = false)
    private int age;
    @Basic
    @Column(name = "sex", nullable = false)
    private int sex;
    @Basic
    @Column(name = "status", nullable = true, length = 32)
    private String status;
    @Basic
    @Column(name = "address", nullable = true, length = 64)
    private String address;
    @Basic
    @Column(name = "phone", nullable = true, length = 12)
    private String phone;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private UsersEntity userId;
    @OneToMany(mappedBy = "humanByFirstPerson")
    private Collection<RelationEntity> relationsById;
    @OneToMany(mappedBy = "humanBySecondPerson")
    private Collection<RelationEntity> relationsById2;

    public HumanEntity(String name, String surname, int age, int sex, String status, String address, String phone, String description, UsersEntity userId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.userId = userId;
    }
}
