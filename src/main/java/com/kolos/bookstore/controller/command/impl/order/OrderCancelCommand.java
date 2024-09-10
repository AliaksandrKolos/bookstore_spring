package com.kolos.bookstore.controller.command.impl.order;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderCancelCommand implements Command {

    private final OrderService orderService;

    @Override
    public String process(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        request.setAttribute("id", id);
        orderService.cancelOrder(id);
        return "jsp/order/ordersUser.jsp";
    }
}
