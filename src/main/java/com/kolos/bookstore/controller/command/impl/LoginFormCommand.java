package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LoginFormCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        return "jsp/loginForm.jsp";
    }
}
