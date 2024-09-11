package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

@Controller("home")
public class HomeCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("lang") == null) {
            session.setAttribute("lang", "en");
        }
        return "jsp/home.jsp";
    }
}
