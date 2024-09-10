package com.kolos.bookstore.data.dao;

import com.kolos.bookstore.data.dto.OrderDto;

import java.util.List;

public interface OrderDao extends AbstractDao<Long, OrderDto> {

    List<OrderDto> findByUserId(Long userId, int limit, int offset);

    int countAll();

    int countAll(String messages);
}
