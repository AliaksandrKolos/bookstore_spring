package com.kolos.bookstore.controller.command.impl.order;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderCommand implements Command {

    private final OrderService orderService;

    @Override
    public String process(HttpServletRequest request) {
        Long orderId = Long.parseLong(request.getParameter("id"));
        OrderDto orderDto = orderService.get(orderId);
        request.setAttribute("order", orderDto);
        return "jsp/order/order.jsp";
    }
}
