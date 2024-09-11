package com.kolos.bookstore.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String command = req.getParameter("command");

        if (isRequireAuthentication(command)) {
            HttpSession session = req.getSession();
            if (session == null || session.getAttribute("user") == null) {
                req.getRequestDispatcher("jsp/error.jsp").forward(req, res);
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private static boolean isRequireAuthentication(String command) {
        return !(command.equals("home")
                || command.equals("books")
                || command.equals("book")
                || command.equals("user_login_form")
                || command.equals("user_login")
                || command.equals("user_registration_form")
                || command.equals("user_registration")
                || command.equals("cart")
                || command.equals("addToCart")
                || command.equals("change_lang")
                || command.equals("books_search"));
    }
}
