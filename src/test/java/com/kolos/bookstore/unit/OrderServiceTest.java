package com.kolos.bookstore.unit;

import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.data.repository.OrderRepository;
import com.kolos.bookstore.service.OrderMapper;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderItemDto;
import com.kolos.bookstore.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final Long ID = 1L;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private EntityManager manager;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void create_shouldReturnOrderDtoSuccessfully() {
        OrderDto orderDto = getOrderDto();

        Order order = new Order();

        when(orderMapper.toEntity(orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.create(orderDto);

        assertNotNull(result);
        assertEquals(new BigDecimal(567), result.getCost());
        verify(orderMapper, times(1)).toEntity(orderDto);
        verify(orderMapper, times(1)).toDto(order);
        verify(orderRepository, times(1)).save(order);
    }

    private static OrderDto getOrderDto() {
        OrderItemDto item1 = new OrderItemDto();
        BookDto book1 = new BookDto();
        book1.setPrice(new BigDecimal(123));
        item1.setBook(book1);
        item1.setQuantity(2);

        OrderItemDto item2 = new OrderItemDto();
        BookDto book2 = new BookDto();
        book2.setPrice(new BigDecimal(321));
        item2.setBook(book2);
        item2.setQuantity(1);

        List<OrderItemDto> items = List.of(item1, item2);

        OrderDto orderDto = new OrderDto();
        orderDto.setItems(items);
        return orderDto;
    }


    @Test
    void getById() {
        Order order = new Order();
        order.setId(ID);

        OrderDto expectedResult = new OrderDto();
        expectedResult.setId(ID);

        Mockito.doReturn(Optional.of(order))
                .when(orderRepository).findById(ID);
        Mockito.doReturn(expectedResult)
                .when(orderMapper).toDto(order);

        var actualResult = orderService.get(ID);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAll() {
        Order order1 = new Order();
        order1.setId(1L);

        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orders = List.of(order1, order2);
        Page<Order> orderpage = new PageImpl<>(orders);

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setId(1L);
        orderDto1.setCost(new BigDecimal(123));

        OrderDto orderDto2 = new OrderDto();
        orderDto2.setId(2L);
        orderDto2.setCost(new BigDecimal(456));

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderpage);
        when(orderMapper.toDto(order1)).thenReturn(orderDto1);
        when(orderMapper.toDto(order2)).thenReturn(orderDto2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<OrderDto> result = orderService.getAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(new BigDecimal(123), result.getContent().get(0).getCost());
        assertEquals(new BigDecimal(456), result.getContent().get(1).getCost());

        verify(orderRepository, times(1)).findAll(pageable);
        verify(orderMapper, times(1)).toDto(order1);
        verify(orderMapper, times(1)).toDto(order2);
    }


}
