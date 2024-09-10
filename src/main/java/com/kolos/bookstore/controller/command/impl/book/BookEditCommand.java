package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class BookEditCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {

        BookDto bookDto = getBookDto(request);
        BookDto bookUpdated = bookService.update(bookDto);
        request.setAttribute("book", bookUpdated);
        return "jsp/book/book.jsp";
    }




    private static BookDto getBookDto(HttpServletRequest request) {
        BookDto bookDto = new BookDto();
        bookDto.setId(Long.parseLong(request.getParameter("id")));
        bookDto.setAuthor(request.getParameter("author"));
        bookDto.setIsbn(request.getParameter("isbn"));
        bookDto.setTitle(request.getParameter("title"));
        bookDto.setGenre(request.getParameter("genre"));
        bookDto.setYear(Integer.parseInt(request.getParameter("year")));
        bookDto.setPages(Integer.parseInt(request.getParameter("pages")));
        bookDto.setPrice(new BigDecimal(request.getParameter("price")));
        bookDto.setCover(BookDto.Cover.valueOf(request.getParameter("cover").toUpperCase()));
        return bookDto;
    }
}
