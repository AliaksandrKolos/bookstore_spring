package com.kolos.bookstore.controller.command.impl.order;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.controller.command.impl.PagingUtil;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.OrderDto;
import com.kolos.bookstore.service.dto.OrderStatusUpdateDto;
import com.kolos.bookstore.service.dto.PageableDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller("change_order_status")
public class OrderChangeStatusCommand implements Command {

    private final OrderService orderService;

    @Override
    public String process(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderStatusUpdateDto.Status status = OrderStatusUpdateDto.Status.valueOf(request.getParameter("status"));
        OrderStatusUpdateDto orderStatusUpdateDto = new OrderStatusUpdateDto();
        orderStatusUpdateDto.setId(id);
        orderStatusUpdateDto.setStatus(status);
        orderService.changeStatus(orderStatusUpdateDto.getId(), orderStatusUpdateDto.getStatus());

        PageableDto pageableDto = PagingUtil.getPageable(request);
        List<OrderDto> orders = orderService.getAll(pageableDto);
        request.setAttribute("orders", orders);
        request.setAttribute("page", pageableDto.getPage());
        request.setAttribute("totalPages", pageableDto.getTotalPages());

        return "jsp/order/orders.jsp";
    }
}
