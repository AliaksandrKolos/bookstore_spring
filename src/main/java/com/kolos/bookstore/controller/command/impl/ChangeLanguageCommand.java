package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

@Controller("change_lang")
public class ChangeLanguageCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        String language = request.getParameter("lang");
        HttpSession session = request.getSession();
        if (session.getAttribute("lang") == null) {
            session.setAttribute("lang", "en");
        }
        session.setAttribute("lang", language);
        return "jsp/home.jsp";
    }
}

