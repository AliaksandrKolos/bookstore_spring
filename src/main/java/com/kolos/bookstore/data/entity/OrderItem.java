package com.kolos.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private Long id;
    private Book book;
    private Integer quantity;
    private BigDecimal price;
}
