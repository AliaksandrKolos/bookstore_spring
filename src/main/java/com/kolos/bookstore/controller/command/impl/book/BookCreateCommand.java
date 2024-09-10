package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
public class BookCreateCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {
        BookDto bookDto = getBookDto(request);
        BookDto createdBook = bookService.create(bookDto);
        request.setAttribute("book", createdBook);
        return "jsp/book/book.jsp";
    }

    private static BookDto getBookDto(HttpServletRequest request) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(request.getParameter("title"));
        bookDto.setAuthor(request.getParameter("author"));
        bookDto.setIsbn(request.getParameter("isbn"));
        bookDto.setGenre(request.getParameter("genre"));
        bookDto.setYear(Integer.parseInt(request.getParameter("year")));
        bookDto.setPages(Integer.parseInt(request.getParameter("pages")));
        bookDto.setPrice(new BigDecimal(request.getParameter("price")));
        bookDto.setCover(BookDto.Cover.valueOf(request.getParameter("cover").toUpperCase()));
        return bookDto;
    }
}
