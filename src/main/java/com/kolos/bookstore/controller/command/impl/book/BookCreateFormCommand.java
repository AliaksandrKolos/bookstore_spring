package com.kolos.bookstore.controller.command.impl.book;

import com.kolos.bookstore.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookCreateFormCommand implements Command {

    @Override
    public String process(HttpServletRequest request) {
        return "jsp/book/bookCreateForm.jsp";
    }
}
