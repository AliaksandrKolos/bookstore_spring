package com.kolos.bookstore.web.controller;

import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.dto.PageableDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
        PageableDto pageableDto = new PageableDto(page, pageSize);
        List<BookDto> books = bookService.getAll(pageableDto);
        addAttribute(model, books, page, pageableDto);
        return "book/books";
    }

    @GetMapping("/create")
    public String createFormBook() {
        return "book/bookCreateForm";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
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
        PageableDto pageableDto = new PageableDto(page, pageSize);
        List<BookDto> books = bookService.getSearchBooks(title, pageableDto);
        addAttribute(model, books, pageableDto.getPage(), pageableDto);
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


    private static void addAttribute(Model model, List<BookDto> books, long pageableDto, PageableDto pageableDto1) {
        model.addAttribute("books", books);
        model.addAttribute("page", pageableDto);
        model.addAttribute("totalPages", pageableDto1.getTotalPages());
    }

}
