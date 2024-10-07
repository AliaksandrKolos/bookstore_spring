package com.kolos.bookstore.web.view;

import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.UserMapper;
import com.kolos.bookstore.service.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        OrderDto orderDto = orderService.get(id);
        model.addAttribute("order", orderDto);
        return "order/order";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/getAll")
    public String getAll(Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        Page<OrderDto> pages = orderService.getAll(pageable);
        addAttribute(model, pageable, pages);
        return "order/orders";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @GetMapping("/orders_user/{id}")
    public String getOrdersUser(@PathVariable Long id, Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        Page<OrderDto> pages = orderService.getOrdersByUserId(id, pageable);
        addAttribute(model, pageable, pages);
        return "order/ordersUser";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping("/change_status")
    public String changeStatusOrder(@ModelAttribute OrderStatusUpdateDto orderStatusUpdateDto) {
        orderService.changeStatus(orderStatusUpdateDto.getId(), orderStatusUpdateDto.getStatus());
        return "redirect:/orders/getAll";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @PostMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','USER')")
    @PostMapping("/order/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@SessionAttribute("cart") Map<BookDto, Integer> cart,
                              @SessionAttribute("user") UserPrincipal userPrincipal,
                              HttpSession session, Model model) {
        OrderDto orderDto = createOrder(cart, userMapper.toDto(userPrincipal.getUser()));
        OrderDto createdOrder = saveOrder(session, orderDto);
        session.removeAttribute("cart");
        model.addAttribute("order", createdOrder);
        return "order/orderConfirmation";
    }


    private OrderDto saveOrder(HttpSession session, OrderDto orderDto) {
        OrderDto createdOrder = orderService.create(orderDto);
        session.setAttribute("order", createdOrder);
        return createdOrder;
    }

    private static OrderDto createOrder(Map<BookDto, Integer> cart, UserDto user) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(user);
        List<OrderItemDto> items = getOrderItemDtos(cart);
        orderDto.setItems(items);
        return orderDto;
    }

    private static List<OrderItemDto> getOrderItemDtos(Map<BookDto, Integer> cart) {
        List<OrderItemDto> orderItems = new ArrayList<>();
        for (BookDto bookDto : cart.keySet()) {
            Integer quantity = cart.get(bookDto);

            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setBook(bookDto);
            itemDto.setQuantity(quantity);
            itemDto.setPrice(bookDto.getPrice());

            orderItems.add(itemDto);
        }

        return orderItems;
    }

    private static void addAttribute(Model model, Pageable pageable, Page<OrderDto> pages) {
        model.addAttribute("orders", pages.getContent());
        model.addAttribute("page", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("size", pageable.getPageSize());
    }
}
