package com.kolos.bookstore.service.dto;

import lombok.Data;

@Data
public class OrderStatusUpdateDto {

    private Long id;
    private Status status;

    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED
    }

}
