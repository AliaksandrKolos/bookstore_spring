package com.kolos.bookstore.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
    private BigDecimal cost;
    private Status status;
    private List<OrderItemDto> items;

    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED
    }
}
