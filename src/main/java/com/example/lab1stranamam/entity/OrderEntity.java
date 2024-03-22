package com.example.lab1stranamam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "order", schema = "public", catalog = "stranamam")
@Data
@NoArgsConstructor
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "payment_type", nullable = false)
    private int paymentType;
    @Basic
    @Column(name = "sum", nullable = false)
    private int sum;
    @Basic
    @Column(name = "sel_time", nullable = true)
    private LocalDateTime selTime;
    @Basic
    @Column(name = "status", nullable = false)
    private int status;
    @ManyToOne
    @JoinColumn(name = "costumer", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByCostumer;
    @ManyToOne
    @JoinColumn(name = "seller", referencedColumnName = "id", nullable = false)
    private UsersEntity usersBySeller;
    @ManyToOne
    @JoinColumn(name = "target", referencedColumnName = "id", nullable = false)
    private UsersEntity usersByTarget;
    @OneToMany(mappedBy = "orderId")
    private Collection<OrderItemEntity> orderItemsById;

    public OrderEntity(int paymentType, int sum, LocalDateTime selTime, int status, UsersEntity usersByCostumer, UsersEntity usersBySeller, UsersEntity usersByTarget) {
        this.paymentType = paymentType;
        this.sum = sum;
        this.selTime = selTime;
        this.status = status;
        this.usersByCostumer = usersByCostumer;
        this.usersBySeller = usersBySeller;
        this.usersByTarget = usersByTarget;
    }
}
