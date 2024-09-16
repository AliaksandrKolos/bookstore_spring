package com.kolos.bookstore.service.mapper;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.entity.OrderItem;
import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.service.DigestService;
import com.kolos.bookstore.service.ServiceMapper;
import com.kolos.bookstore.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceMapperImpl implements ServiceMapper {

    private final DigestService digestService;

    public User toEntityRegistrationUser(UserRegistrationDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(digestService.hash(dto.getPassword()));
        return user;
    }

    public User toEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(User.Role.valueOf(dto.getRole().name()));
        return entity;
    }

    @Override
    public UserDto toDtoRegistrationUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(UserDto.Role.USER);
        return userDto;
    }

    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setRole(UserDto.Role.valueOf(entity.getRole().name()));
        return dto;
    }

    public OrderDto toDto(Order entity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(this.toDto(entity.getUser()));
        List<OrderItemDto> orderItemDtos = entity.getItems().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        orderDto.setItems(orderItemDtos);
        orderDto.setId(entity.getId());
        orderDto.setCost(entity.getCost());
        orderDto.setStatus(OrderDto.Status.valueOf(entity.getStatus().name()));
        return orderDto;
    }

    public OrderItemDto toDto(OrderItem entity) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(entity.getId());
        orderItemDto.setQuantity(entity.getQuantity());
        orderItemDto.setPrice(entity.getPrice());
        orderItemDto.setBook(toDto(entity.getBook()));
        return orderItemDto;
    }

    public Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(toEntity(dto.getUser()));
        List<OrderItem> orderItems = dto.getItems().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        order.setItems(orderItems);
        order.setCost(dto.getCost());
        order.setStatus(Order.Status.valueOf(dto.getStatus().name()));
        return order;
    }

    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());
        orderItem.setBook(toEntity(dto.getBook()));
        return orderItem;
    }

    public BookDto toDto(Book entity) {
        BookDto bookDto = new BookDto();
        bookDto.setId(entity.getId());
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setIsbn(entity.getIsbn());
        bookDto.setTitle(entity.getTitle());
        bookDto.setPrice(entity.getPrice());
        bookDto.setYear(entity.getYear());
        bookDto.setPages(entity.getPages());
        bookDto.setGenre(entity.getGenre());
        bookDto.setCover(BookDto.Cover.valueOf(entity.getCover().name()));
        return bookDto;
    }

    public BookDto toDtoShort(Book entity) {
        BookDto bookDto = new BookDto();
        bookDto.setId(entity.getId());
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setTitle(entity.getTitle());
        bookDto.setPages(entity.getPages());
        return bookDto;
    }

    public Book toEntity(BookDto dto) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setIsbn(dto.getIsbn());
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setPages(dto.getPages());
        entity.setGenre(dto.getGenre());
        entity.setAuthor(dto.getAuthor());
        entity.setYear(dto.getYear());
        entity.setCover(Book.Cover.valueOf(dto.getCover().name()));
        return entity;
    }
}
