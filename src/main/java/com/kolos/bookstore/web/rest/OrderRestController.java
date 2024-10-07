package com.kolos.bookstore.web.rest;


import com.kolos.bookstore.data.entity.Order;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

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

//    TODO
//    @PostMapping
//    public ResponseEntity<OrderDto> create(@RequestBody CreatedOrderRequest createdOrderRequest) {
//        OrderDto orderDto =  new OrderDto();
//        createdOrderDto(createdOrderRequest, orderDto);
//        OrderDto created = orderService.create(orderDto);
//        return buildResponseCreated(created);
//    }
//
//
//    private ResponseEntity<OrderDto> buildResponseCreated(OrderDto created) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .location(getLocation(created))
//                .body(created);
//    }
//
//    private URI getLocation(OrderDto orderDto) {
//        return ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/orders/{id}")
//                .buildAndExpand(orderDto.getId())
//                .toUri();
//    }
//
//
//    private static List<OrderItemDto> getOrderItemDtos(Map<BookDto, Integer> cart) {
//        List<OrderItemDto> orderItemDtos = new ArrayList<>();
//        for(BookDto bookDto: cart.keySet()) {
//            Integer quantity = cart.get(bookDto);
//            OrderItemDto orderItemDto = new OrderItemDto();
//            orderItemDto.setQuantity(quantity);
//            orderItemDto.setBook(bookDto);
//            orderItemDto.setPrice(bookDto.getPrice());
//            orderItemDtos.add(orderItemDto);
//        }
//        return orderItemDtos;
//    }
//
//
//    private static void createdOrderDto(CreatedOrderRequest createdOrderRequest, OrderDto orderDto) {
//        UserDto userDto = createdOrderRequest.getUser();
//        Map<BookDto, Integer> cart = createdOrderRequest.getCart();
//        orderDto.setUser(userDto);
//        List<OrderItemDto> orderItemDtos = getOrderItemDtos(cart);
//        orderDto.setItems(orderItemDtos);
//    }












}
