package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user_registration_form")
public class UserRegistrationFormCommand implements Command {

    @Override
    public String process(HttpServletRequest request) {
        return "jsp/user/userRegistrationForm.jsp";
    }
}
