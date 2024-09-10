package com.kolos.bookstore.controller.command.impl;

import com.kolos.bookstore.controller.command.Command;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AddToCartCommand implements Command {

    private final BookService bookService;

    @Override
    public String process(HttpServletRequest request) {
        Long bookId = Long.parseLong(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();
        Map<BookDto, Integer> cart = (Map<BookDto, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        BookDto bookDto = bookService.get(bookId);
        cart.merge(bookDto, quantity, Integer::sum);

        return "controller?command=books";
    }
}
