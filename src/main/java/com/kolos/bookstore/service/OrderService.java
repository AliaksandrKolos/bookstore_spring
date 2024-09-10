package com.kolos.bookstore.service;


import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getOrdersByUserId(Long userId, PageableDto pageableDto);

    void changeStatus(Long orderId, OrderStatusUpdateDto.Status dtoStatus);

    void cancelOrder(Long orderId);

    OrderDto get(Long id);

    List<OrderDto> getAll(PageableDto pageableDto);

    OrderDto create(OrderDto dto);


}
