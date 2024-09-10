package com.kolos.bookstore.controller.command.impl.order;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderItemDto;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderCreateCommand implements Command {

    private final OrderService orderService;

    @Override
    public String process(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<BookDto, Integer> cart = (Map<BookDto, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:jsp/cart.jsp";
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setUser((UserDto) session.getAttribute("user"));

        BigDecimal totalCost = getTotalCost(cart);
        orderDto.setCost(totalCost);

        List<OrderItemDto> items = getOrderItemDtos(cart);
        orderDto.setItems(items);

        OrderDto createdOrder = orderService.create(orderDto);
        session.setAttribute("order", createdOrder);

        session.removeAttribute("cart");

        return "jsp/order/orderConfirmation.jsp";
    }

    private static List<OrderItemDto> getOrderItemDtos(Map<BookDto, Integer> cart) {
        List<OrderItemDto> items = cart.entrySet().stream()
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
        return items;
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
}
