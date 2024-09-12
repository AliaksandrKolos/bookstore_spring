package com.kolos.bookstore.data.repository.impl;

import com.kolos.bookstore.data.dao.BookDao;
import com.kolos.bookstore.data.dao.OrderDao;
import com.kolos.bookstore.data.dao.OrderItemDao;
import com.kolos.bookstore.data.dao.UserDao;
import com.kolos.bookstore.data.dto.BookDto;
import com.kolos.bookstore.data.dto.OrderDto;
import com.kolos.bookstore.data.dto.OrderItemDto;
import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.entity.OrderItem;
import com.kolos.bookstore.data.entity.User;
import com.kolos.bookstore.data.mapper.DataMapper;
import com.kolos.bookstore.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final BookDao bookDao;
    private final OrderItemDao orderItemDao;
    private final DataMapper dataMapper;

    @Override
    public Order find(Long id) {
        OrderDto orderDto = orderDao.find(id);
        return combineOrder(orderDto);
    }

    @Override
    public List<Order> findAll(int limit, int offset) {
        return orderDao.findAll(limit, offset)
                .stream()
                .map(this::combineOrder)
                .collect(Collectors.toList());
    }


    @Override
    public Order save(Order entity) {
        OrderDto orderDto = dataMapper.toDto(entity);
        OrderDto savedOrderDto = orderDao.save(orderDto);

        entity.getItems().forEach(orderItem -> {
            OrderItemDto orderItemDto = dataMapper.toDto(orderItem);
            orderItemDto.setOrderId(savedOrderDto.getId());
            orderItemDao.save(orderItemDto);
        });
        return combineOrder(savedOrderDto);
    }

    @Override
    public Order update(Order order) {
        OrderDto orderDto = dataMapper.toDto(order);
        OrderDto updatedOrderDto = orderDao.update(orderDto);
        orderItemDao.findByOrderId(order.getId())
                .forEach(oldOrderItem -> orderItemDao.delete(oldOrderItem.getId()));
        order.getItems().forEach(orderItem -> {
            OrderItemDto orderItemDto = dataMapper.toDto(orderItem);
            orderItemDto.setOrderId(updatedOrderDto.getId());
            orderItemDao.save(orderItemDto);
        });
        return combineOrder(updatedOrderDto);
    }


    @Override
    public boolean delete(Long id) {
        if (!orderDao.delete(id)) {
            return false;
        }
        orderItemDao.findByOrderId(id)
                .forEach(orderItemDto -> orderItemDao.delete(orderItemDto.getId()));
        return true;
    }

    @Override
    public List<Order> findByUserId(Long userId, int limit, int offset) {
        return orderDao.findByUserId(userId, limit, offset)
                .stream()
                .map(this::combineOrder)
                .collect(Collectors.toList());
    }

    @Override
    public int countAll() {
        return orderDao.countAll();
    }

    @Override
    public int countAllMassage(Long id) {
        return orderDao.countAll(id);
    }


    private Order combineOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCost(orderDto.getCost());
        order.setStatus(Order.Status.valueOf(orderDto.getStatus().name()));

        Long userId = orderDto.getUserId();
        com.kolos.bookstore.data.dto.UserDto userDto = userDao.find(userId);
        User user = dataMapper.toEntity(userDto);
        order.setUser(user);

        Long orderId = orderDto.getId();
        List<OrderItemDto> orderItemDto = orderItemDao.findByOrderId(orderId);
        List<OrderItem> details = new ArrayList<>();
        orderItemDto.forEach(dto -> {
            OrderItem entity = dataMapper.toEntity(dto);

            BookDto bookDto = bookDao.find(dto.getBookId());
            Book book = dataMapper.toEntity(bookDto);

            entity.setBook(book);
            details.add(entity);
        });
        order.setItems(details);

        return order;
    }


}
