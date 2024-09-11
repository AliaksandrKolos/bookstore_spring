package com.kolos.bookstore.controller.command.impl.user;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.UserService;
import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.kolos.bookstore.controller.command.impl.PagingUtil.getPageable;

@Controller("users_search_last_name")
@RequiredArgsConstructor
public class UserSearchByLastNameCommand implements Command {

    private final UserService userService;

    @Override
    public String process(HttpServletRequest request) {

        String lastName = request.getParameter("lastName");
        PageableDto pageableDto = getPageable(request);
        List<UserDto>  users = userService.getByLastName(lastName, pageableDto);

        request.setAttribute("users", users);
        request.setAttribute("lastName", lastName);
        request.setAttribute("totalPages", pageableDto.getTotalPages());


        return "jsp/user/usersSearch.jsp";
    }
}
