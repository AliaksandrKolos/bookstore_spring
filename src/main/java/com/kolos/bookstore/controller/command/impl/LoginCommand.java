package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user_login")
@RequiredArgsConstructor
public class LoginCommand implements Command {

    private final UserService userService;

    @Override
    public String process(HttpServletRequest request) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserDto userDto = userService.login(email, password);
        HttpSession session = request.getSession();
        session.setAttribute("user", userDto);
        return "jsp/home.jsp";
    }
}
