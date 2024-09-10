package com.kolos.bookstore.data.mapper;

import com.kolos.bookstore.data.dto.BookDto;
import com.kolos.bookstore.data.dto.OrderDto;
import com.kolos.bookstore.data.dto.OrderItemDto;
import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.entity.OrderItem;
import com.kolos.bookstore.data.entity.User;

public class DataMapperImpl implements DataMapper{


    @Override

    public User toEntity(com.kolos.bookstore.data.dto.UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setRole(User.Role.valueOf(userDto.getRole().name()));
        return user;
    }

    @Override
    public com.kolos.bookstore.data.dto.UserDto toDto(User user) {
        com.kolos.bookstore.data.dto.UserDto userDto = new com.kolos.bookstore.data.dto.UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setId(user.getId());
        userDto.setRole(com.kolos.bookstore.data.dto.UserDto.Role.valueOf(user.getRole().name()));
        return userDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setPrice(bookDto.getPrice());
        book.setPages(bookDto.getPages());
        book.setGenre(bookDto.getGenre());
        book.setAuthor(bookDto.getAuthor());
        book.setYear(bookDto.getYear());
        book.setCover(Book.Cover.valueOf(bookDto.getCover().name()));
        return book;
    }

    @Override
    public Book toShortEntity(BookDto bookDto) {
            Book book = new Book();
            book.setId(bookDto.getId());
            book.setAuthor(bookDto.getAuthor());
            book.setTitle(bookDto.getTitle());
            book.setPages(bookDto.getPages());
            return book;
    }

    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setPrice(book.getPrice());
        bookDto.setPages(book.getPages());
        bookDto.setGenre(book.getGenre());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setYear(book.getYear());
        bookDto.setCover(BookDto.Cover.valueOf(book.getCover().name()));
        return bookDto;
    }

    @Override
    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();
        entity.setId(dto.getId());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    @Override
    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setCost(order.getCost());
        orderDto.setStatus(OrderDto.Status.valueOf(order.getStatus().toString()));
        return orderDto;
    }

    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setBookId(orderItem.getBook().getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }

}
