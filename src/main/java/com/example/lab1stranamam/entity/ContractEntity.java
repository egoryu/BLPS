package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "contract")
@Data
@NoArgsConstructor
public class ContractEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Basic
    @Column(name = "type", nullable = false)
    private int type;
    @ManyToOne
    @JoinColumn(name = "master", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByMaster;
    @ManyToOne
    @JoinColumn(name = "contractor", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByContractor;

    public ContractEntity(LocalDateTime date, int type, UsersEntity usersByMaster, UsersEntity usersByContractor) {
        this.date = date;
        this.type = type;
        this.usersByMaster = usersByMaster;
        this.usersByContractor = usersByContractor;
    }
}
