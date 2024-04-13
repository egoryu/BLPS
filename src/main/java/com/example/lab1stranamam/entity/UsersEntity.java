package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "username", nullable = true, length = 32)
    private String username;
    @Basic
    @Column(name = "email", nullable = false, length = 32)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    @Basic
    @Column(name = "role", nullable = false, length = 32)
    private String role;
    @OneToMany(mappedBy = "usersByMaster")
    private Collection<ContractEntity> contractsById;
    @OneToMany(mappedBy = "usersByContractor")
    private Collection<ContractEntity> contractsById_0;
    @OneToMany(mappedBy = "userId")
    private Collection<HumanEntity> humansById;
    @OneToMany(mappedBy = "usersByFrom")
    private Collection<MessageEntity> messagesById;
    @OneToMany(mappedBy = "usersByTo")
    private Collection<MessageEntity> messagesById_0;
    @OneToMany(mappedBy = "usersByCostumer")
    private Collection<OrderEntity> ordersById;
    @OneToMany(mappedBy = "usersBySeller")
    private Collection<OrderEntity> ordersById_0;
    @OneToMany(mappedBy = "usersByTarget")
    private Collection<OrderEntity> ordersById_1;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<WalletEntity> walletsById;

    public UsersEntity(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
