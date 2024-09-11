package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller("book_create_form")
public class BookCreateFormCommand implements Command {

    @Override
    public String process(HttpServletRequest request) {
        return "jsp/book/bookCreateForm.jsp";
    }
}
