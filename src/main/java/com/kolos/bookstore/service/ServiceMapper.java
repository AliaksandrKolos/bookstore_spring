package com.kolos.bookstore.service;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.entity.OrderItem;
import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.service.dto.*;

public interface ServiceMapper {

    // User mappings
    User toEntityRegistrationUser(UserRegistrationDto userRegistrationDto);

    UserDto toDto(User entity);

    User toEntity(UserDto dto);

    UserDto toDtoRegistrationUser(User user);

    // Order mappings
    OrderDto toDto(Order order);

    Order toEntity(OrderDto dto);

    OrderItemDto toDto(OrderItem item);

    OrderItem toEntity(OrderItemDto dto);

    // Book mappings
    BookDto toDto(Book entity);

    BookDto toDtoShort(Book entity);

    Book toEntity(BookDto dto);
}
