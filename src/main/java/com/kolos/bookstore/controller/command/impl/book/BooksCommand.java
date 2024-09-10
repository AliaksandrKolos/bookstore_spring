package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.kolos.bookstore.controller.command.impl.PagingUtil.getPageable;

@RequiredArgsConstructor
public class BooksCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {

        PageableDto pageableDto = getPageable(request);
        List<BookDto> books = bookService.getAll(pageableDto);

        request.setAttribute("books", books);
        request.setAttribute("page", pageableDto.getPage());
        request.setAttribute("totalPages", pageableDto.getTotalPages());
        return "jsp/book/books.jsp";
    }
}
