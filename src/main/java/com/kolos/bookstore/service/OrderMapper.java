package com.kolos.bookstore.service;

import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.service.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    Order toEntity(OrderDto dto);

}
