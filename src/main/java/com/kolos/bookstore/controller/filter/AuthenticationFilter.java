package com.kolos.bookstore.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
        return !(command.equals("homeCommand")
                || command.equals("booksCommand")
                || command.equals("bookCommand")
                || command.equals("loginFormCommand")
                || command.equals("loginCommand")
                || command.equals("UserRegistrationForm")
                || command.equals("userRegistrationCommand")
                || command.equals("cartCommand")
                || command.equals("addToCartCommand")
                || command.equals("changeLanguageCommand")
                || command.equals("booksSearchCommand"));
    }
}
