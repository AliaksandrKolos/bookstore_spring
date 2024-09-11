package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user_edit")
@RequiredArgsConstructor
public class UserEditCommand implements Command {

    private final UserService userService;

    @Override
    public String process(HttpServletRequest request) {
        UserDto userDto = new UserDto();
        userDto.setId(Long.parseLong(request.getParameter("id")));
        userDto.setFirstName(request.getParameter("firstName"));
        userDto.setLastName(request.getParameter("lastName"));
        userDto.setEmail(request.getParameter("email"));
        userDto.setPassword(request.getParameter("password"));
        userDto.setRole(UserDto.Role.valueOf(request.getParameter("role")));

        UserDto editedUser = userService.update(userDto);
        request.setAttribute("user", editedUser);

        return "jsp/user/user.jsp";
    }
}
