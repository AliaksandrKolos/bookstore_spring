package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class UserRegistrationForm implements Command {

    @Override
    public String process(HttpServletRequest request) {
        return "jsp/user/userRegistrationForm.jsp";
    }
}
