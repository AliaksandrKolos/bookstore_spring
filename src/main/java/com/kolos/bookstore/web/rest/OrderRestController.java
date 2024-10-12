package com.kolos.bookstore.web.rest;


import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;
    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping("/orders_user/{id}")
    public Page<OrderDto> getOrderByUserId(@PathVariable Long id, Pageable pageable) {
        return orderService.getOrdersByUserId(id, pageable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @PatchMapping("/{id}")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public void changeStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateDto orderStatusUpdateDto) {
        orderStatusUpdateDto.setId(id);
        orderService.changeStatus(orderStatusUpdateDto.getId(), orderStatusUpdateDto.getStatus());
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        UserDto userDto = userService.get(orderRequest.getUserId());
        OrderDto orderDto = createOrder(orderRequest.getCart(), userDto);
        OrderDto createdOrder = orderService.create(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }


    private OrderDto createOrder(Map<Long, Integer> cart, UserDto user) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(user);

        List<OrderItemDto> items = getOrderItemDtos(cart);
        orderDto.setItems(items);

        return orderDto;
    }

    private List<OrderItemDto> getOrderItemDtos(Map<Long, Integer> cart) {
        List<OrderItemDto> orderItems = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Long bookId = entry.getKey();
            Integer quantity = entry.getValue();

            BookDto bookDto = bookService.get(bookId);

            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setBook(bookDto);
            itemDto.setQuantity(quantity);
            itemDto.setPrice(bookDto.getPrice());

            orderItems.add(itemDto);
        }

        return orderItems;
    }
}
