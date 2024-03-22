package com.example.lab1stranamam.repositories;

import com.example.lab1stranamam.entity.OrderEntity;
import com.example.lab1stranamam.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Integer> {
    void deleteAllByOrderId(OrderEntity order);
}
