package com.kolos.bookstore.service;

import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<OrderDto> getOrdersByUserId(Long id, Pageable pageable);

    void changeStatus(Long orderId, OrderStatusUpdateDto.Status dtoStatus);

    void cancelOrder(Long orderId);

    OrderDto get(Long id);

    Page<OrderDto> getAll(Pageable pageable);

    OrderDto create(OrderDto dto);


}
