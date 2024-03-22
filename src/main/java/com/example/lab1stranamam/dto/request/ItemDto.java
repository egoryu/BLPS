package com.example.lab1stranamam.dto.request;

import com.example.lab1stranamam.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
    private int id;
    private String name;
    private int price;
    private String description;

    public ItemDto(ItemEntity item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.description = item.getDescription();
    }
}
