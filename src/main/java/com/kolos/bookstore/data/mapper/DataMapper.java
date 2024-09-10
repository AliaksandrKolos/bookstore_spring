package com.kolos.bookstore.data.mapper;

import com.kolos.bookstore.data.dto.BookDto;
import com.kolos.bookstore.data.dto.OrderDto;
import com.kolos.bookstore.data.dto.OrderItemDto;
import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.entity.OrderItem;
import com.kolos.bookstore.data.entity.User;

public interface DataMapper {


    BookDto toDto(Book book);

    com.kolos.bookstore.data.dto.UserDto toDto(User user);

    OrderDto toDto(Order order);

    OrderItemDto toDto(OrderItem orderItem);

    Book toEntity(BookDto bookDto);

    Book toShortEntity(BookDto bookDto);

    User toEntity(com.kolos.bookstore.data.dto.UserDto userDto);

    OrderItem toEntity(OrderItemDto dto);
}
