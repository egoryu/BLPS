package com.example.lab1stranamam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDto {
    private int orderId;
    private int paymentType;
    private int sum;
    private LocalDateTime data;
    private int userId;
    private int traderId;
    private int contractorId;
}
