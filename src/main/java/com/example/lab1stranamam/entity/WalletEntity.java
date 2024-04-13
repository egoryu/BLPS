package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
public class WalletEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "amount", nullable = false)
    private int amount;
    @OneToMany(mappedBy = "walletByWalletId")
    private Collection<ItemEntity> itemsById;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByUserId;

    public WalletEntity(UsersEntity usersByUserId) {
        this.amount = 0;
        this.usersByUserId = usersByUserId;
    }
}
