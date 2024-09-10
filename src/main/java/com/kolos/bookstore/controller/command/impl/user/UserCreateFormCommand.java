package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class UserCreateFormCommand implements Command {
    @Override
    public String process(HttpServletRequest request) {
        return "jsp/user/userCreateForm.jsp";
    }
}
