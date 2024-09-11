package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;


@Slf4j
@RequiredArgsConstructor
@Controller("user_create")
public class UserCreateCommand implements Command {

    private final UserService userService;

    @Override
    public String process(HttpServletRequest request) {
        UserDto userDto = getUserDto(request);
        UserDto createdUser = userService.create(userDto);
        request.setAttribute("user", createdUser);
        return "jsp/user/user.jsp";
    }

    private static UserDto getUserDto(HttpServletRequest request) {
        UserDto userDto = new UserDto();

        userDto.setFirstName(request.getParameter("firstName"));
        userDto.setLastName(request.getParameter("lastName"));
        userDto.setEmail(request.getParameter("email"));
        userDto.setPassword(request.getParameter("password"));
        userDto.setRole(UserDto.Role.valueOf(request.getParameter("role").toUpperCase()));

        return userDto;
    }

}
