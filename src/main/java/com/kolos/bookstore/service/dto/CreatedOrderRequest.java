package com.kolos.bookstore.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CreatedOrderRequest {

    private UserDto user;
    private Map<BookDto, Integer> cart;
}
