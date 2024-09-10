package com.kolos.bookstore.controller.command.impl.order;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.controller.command.impl.PagingUtil;
import com.kolos.bookstore.service.OrderService;
import com.kolos.bookstore.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrdersCommand implements Command {

    private final OrderService orderService;

    @Override
    public String process(HttpServletRequest request) {

        PageableDto pageableDto = PagingUtil.getPageable(request);
        List<OrderDto> orders = orderService.getAll(pageableDto);
        request.setAttribute("orders", orders);
        request.setAttribute("page", pageableDto.getPage());
        request.setAttribute("totalPages", pageableDto.getTotalPages());
        return "jsp/order/orders.jsp";
    }
}
