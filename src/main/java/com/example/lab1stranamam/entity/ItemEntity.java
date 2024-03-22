package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "item", schema = "public", catalog = "stranamam")
@Data
@NoArgsConstructor
public class ItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Basic
    @Column(name = "price", nullable = false)
    private int price;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
    private WalletEntity walletByWalletId;
    @OneToMany(mappedBy = "itemByItem")
    private Collection<OrderItemEntity> orderItemsById;

    public ItemEntity(String name, int price, String description, WalletEntity walletByWalletId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.walletByWalletId = walletByWalletId;
    }
}
