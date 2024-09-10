package com.kolos.bookstore.controller.command.impl.order;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderChangeStatus implements Command {

    private final OrderService orderService;

    @Override
    public String process(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderStatusUpdateDto.Status status = OrderStatusUpdateDto.Status.valueOf(request.getParameter("status"));
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto();
        orderStatusUpdateDto.setId(id);
        orderStatusUpdateDto.setStatus(status);
        orderService.changeStatus(orderStatusUpdateDto.getId(), orderStatusUpdateDto.getStatus());

        return "jsp/order/orders.jsp";
    }
}
