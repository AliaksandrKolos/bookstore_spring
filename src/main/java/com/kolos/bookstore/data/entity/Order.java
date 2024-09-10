package com.kolos.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {

    private Long id;
    private User user;
    private BigDecimal cost;
    private Status status = Status.PENDING;
    private List<OrderItem> items;

    public enum Status {
        PENDING, PAID, DELIVERED, CANCELLED
    }
}
