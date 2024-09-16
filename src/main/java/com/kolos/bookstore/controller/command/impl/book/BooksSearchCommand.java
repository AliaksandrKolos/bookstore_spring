package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.dto.PageableDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.kolos.bookstore.controller.command.impl.PagingUtil.getPageable;
@Slf4j
@RequiredArgsConstructor
@Controller("books_search")
public class BooksSearchCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {

        String searchMessage = request.getParameter("search");
        PageableDto pageableDto = getPageable(request);
        List<BookDto> books = bookService.getSearchBooks(searchMessage, pageableDto);

        request.setAttribute("page", pageableDto.getPage());
        request.setAttribute("totalPages", pageableDto.getTotalPages());
        request.setAttribute("books", books);

        return "jsp/book/booksSearch.jsp";
    }

}
