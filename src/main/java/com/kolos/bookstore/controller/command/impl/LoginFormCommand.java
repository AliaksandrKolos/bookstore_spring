package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user_login_form")
public class LoginFormCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        return "jsp/loginForm.jsp";
    }
}
