package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String lang = (String) session.getAttribute("lang");
        session.invalidate();
        request.getSession().setAttribute("lang", lang);
        return "jsp/home.jsp";
    }
}
