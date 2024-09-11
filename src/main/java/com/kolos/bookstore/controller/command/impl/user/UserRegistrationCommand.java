package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import com.kolos.bookstore.service.dto.UserRegistrationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user_registration")
@RequiredArgsConstructor
public class UserRegistrationCommand implements Command {

    private final UserService userService;

    @Override
    public String process(HttpServletRequest request) {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        setParameters(request, userRegistrationDto);
        UserDto userDto = new UserDto();
        createUseDto(userDto, userRegistrationDto);
        request.setAttribute("user", userDto);
        return "jsp/user/user.jsp";
    }

    private static void createUseDto(UserDto userDto, UserRegistrationDto userRegistrationDto) {
        userDto.setEmail(userRegistrationDto.getEmail());
        userDto.setPassword(userRegistrationDto.getPassword());
    }

    private void setParameters(HttpServletRequest request, UserRegistrationDto userRegistrationDto) {
        userRegistrationDto.setEmail(request.getParameter("email"));
        userRegistrationDto.setPassword(request.getParameter("password"));
        userService.registration(userRegistrationDto);
    }
}
