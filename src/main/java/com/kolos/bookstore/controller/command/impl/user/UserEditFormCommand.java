package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user_edit_form")
@RequiredArgsConstructor
public class UserEditFormCommand implements Command {

    private final UserService userService;

    @Override
    public String process(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        UserDto userDto = userService.get(id);
        request.setAttribute("user", userDto);
        return "jsp/user/userEditForm.jsp";
    }
}
