package com.kolos.bookstore.data.dao;

import com.kolos.bookstore.data.dto.OrderItemDto;

import java.util.List;

public interface OrderItemDao  {


    List<OrderItemDto> findByOrderId(Long id);

    OrderItemDto find(Long id);

    List<OrderItemDto> findAll();

    OrderItemDto save(OrderItemDto dto);

    boolean delete(Long id);


}
