package com.kolos.bookstore.web.rest;


import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    public OrderDto get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping("/orders_user/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    public Page<OrderDto> getOrderByUserId(@PathVariable Long id, Pageable pageable) {
        return orderService.getOrdersByUserId(id, pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
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
