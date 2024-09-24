package com.kolos.bookstore.web.controller;

import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        BookDto bookDto = bookService.get(id);
        model.addAttribute("book", bookDto);
        return "book/book";
    }

    @GetMapping("/getAll")
    public String getAllBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size", defaultValue = "5") int pageSize,
            Model model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<BookDto> pages = bookService.getAll(pageable);
        addAttribute(model, pages);
        return "book/books";
    }

    @GetMapping("/create")
    public String createFormBook() {
        return "book/bookCreateForm";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute BookDto bookDto, Model model) {
        BookDto createdBook = bookService.create(bookDto);
        model.addAttribute("book", createdBook);
        return "redirect:/books/" + createdBook.getId();
    }

    @GetMapping("/edit/{id}")
    public String editFormBook(@PathVariable Long id, Model model) {
        BookDto bookDto = bookService.get(id);
        model.addAttribute("book", bookDto);
        return "book/bookEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@ModelAttribute BookDto bookDto) {
        BookDto bookUpdated = bookService.update(bookDto);
        return "redirect:/books/" + bookUpdated.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books/getAll";
    }

    @GetMapping("/search_title")
    public String getBookByTitle(@RequestParam String title, Model model,
                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "page_size", defaultValue = "5") int pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<BookDto> pages = bookService.getSearchBooks(title, pageable);
        addAttribute(model, pages);
        return "book/booksSearch";
    }

    @GetMapping("/addCart")
    public String addToCart(@RequestParam Long id, @RequestParam Integer quantity, HttpSession session) {
        Map<BookDto, Integer> cart = (Map<BookDto, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        BookDto bookDto = bookService.get(id);
        cart.merge(bookDto, quantity, Integer::sum);
        return "redirect:/books/getAll";
    }

    private static void addAttribute(Model model, Page<BookDto> pages) {
        model.addAttribute("books", pages.getContent());
        model.addAttribute("page", pages.getNumber());
        model.addAttribute("totalPages", pages.getTotalPages());
    }
}
