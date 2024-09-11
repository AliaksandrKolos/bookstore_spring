package com.kolos.bookstore.controller.filter;

import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AuthorizationRoleFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String command = req.getParameter("command");

        if (command != null && CommandSecurityList.INSTANCE.isRestricted(command)) {
            HttpSession session = req.getSession();

            if (session == null || session.getAttribute("user") == null) {
                res.sendRedirect("login.jsp");
                return;
            }

            UserDto user = (UserDto) session.getAttribute("user");

            if (!CommandSecurityList.INSTANCE.isCommandAllowedForRole(command, user.getRole())) {
                req.getRequestDispatcher("jsp/error.jsp").forward(req, res);
                return;
            }
        }

        chain.doFilter(req, res);
    }
}
