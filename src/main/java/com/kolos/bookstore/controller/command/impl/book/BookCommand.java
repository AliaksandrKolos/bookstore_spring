package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        BookDto bookDto = bookService.get(id);
        request.setAttribute("book", bookDto);
        return "jsp/book/book.jsp";
    }
}
