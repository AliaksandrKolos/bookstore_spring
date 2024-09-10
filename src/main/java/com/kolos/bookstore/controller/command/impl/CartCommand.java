package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CartCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        return "jsp/cart.jsp";
    }
}
