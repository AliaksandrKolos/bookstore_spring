package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.controller.command.impl.PagingUtil;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller("users")
@RequiredArgsConstructor
public class UsersCommand implements Command {

    private final UserService userService;


    @Override
    public String process(HttpServletRequest request) {

        PageableDto pageableDto = PagingUtil.getPageable(request);
        List<UserDto> users = userService.getAll(pageableDto);
        request.setAttribute("users", users);
        request.setAttribute("page", pageableDto.getPage());
        request.setAttribute("totalPages", pageableDto.getTotalPages());
        return "jsp/user/users.jsp";
    }
}
