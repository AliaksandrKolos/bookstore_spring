package com.kolos.bookstore.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderRequest {

    private Long userId;
    private Map<Long, Integer> cart;
}
