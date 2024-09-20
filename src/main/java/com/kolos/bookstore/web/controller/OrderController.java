package com.kolos.bookstore.web.controller;

import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) {
        OrderDto orderDto = orderService.get(id);
        model.addAttribute("order", orderDto);
        return "order/order";
    }

    @GetMapping("/getAll")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "5") int pageSize,
            Model model) {
        PageableDto pageableDto = new PageableDto(page, pageSize);
        List<OrderDto> orders = orderService.getAll(pageableDto);
        addAttribute(model, orders, pageableDto);
        return "order/orders";
    }

    @GetMapping("/orders_user/{id}")
    public String getOrdersUser(@PathVariable Long id, Model model,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "page_size", defaultValue = "5") int pageSize) {
        PageableDto pageableDto = new PageableDto(page, pageSize);
        List<OrderDto> orders = orderService.getOrdersByUserId(id, pageableDto);
        addAttribute(model, orders, pageableDto);
        return "order/ordersUser";
    }

    @PostMapping("/change_status")
    public String changeStatusOrder(@ModelAttribute OrderStatusUpdateDto orderStatusUpdateDto) {
        orderService.changeStatus(orderStatusUpdateDto.getId(), orderStatusUpdateDto.getStatus());
        return "redirect:/orders/getAll";
    }

    @PostMapping("/cancelOrder/{id}")
    public String cancelOrder(@PathVariable Long id, Model model) {
        orderService.cancelOrder(id);
        return "redirect:/orders/getAll";
    }

    @PostMapping("/order/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@SessionAttribute("cart") Map<BookDto, Integer> cart,
                              @SessionAttribute("user") UserDto user,
                              HttpSession session, Model model) {
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }
        OrderDto orderDto = createOrder(cart, user);
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
        orderDto.setCost(getTotalCost(cart));

        List<OrderItemDto> items = getOrderItemDtos(cart);
        orderDto.setItems(items);
        return orderDto;
    }

    private static List<OrderItemDto> getOrderItemDtos(Map<BookDto, Integer> cart) {
        return cart.entrySet().stream()
                .map(entry -> {
                    BookDto bookDto = entry.getKey();
                    Integer quantity = entry.getValue();
                    OrderItemDto itemDto = new OrderItemDto();
                    itemDto.setBook(bookDto);
                    itemDto.setQuantity(quantity);
                    itemDto.setPrice(bookDto.getPrice());
                    return itemDto;
                })
                .collect(Collectors.toList());
    }

    private static BigDecimal getTotalCost(Map<BookDto, Integer> cart) {
        return cart.entrySet().stream()
                .map(entry -> {
                    BookDto bookDto = entry.getKey();
                    Integer quantity = entry.getValue();
                    return bookDto.getPrice().multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void addAttribute(Model model, List<OrderDto> orders, PageableDto pageableDto) {
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageableDto.getPage());
        model.addAttribute("totalPages", pageableDto.getTotalPages());
    }
}
