package com.example.lab1stranamam.dto.request;

import com.example.lab1stranamam.entity.WalletEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WalletDto {
    private int userId;
    private int sum;
    private List<ItemDto> items;

    public WalletDto(WalletEntity wallet) {
        this.userId = wallet.getId();
        this.sum = wallet.getAmount();
        this.items = wallet.getItemsById().stream().map(ItemDto::new).toList();
    }
}
