package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("error")
public class ErrorCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        return "jsp/error.jsp";
    }
}
