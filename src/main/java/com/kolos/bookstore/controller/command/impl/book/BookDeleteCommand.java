package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class
BookDeleteCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        bookService.delete(id);
        return "jsp/home.jsp";
    }
}
