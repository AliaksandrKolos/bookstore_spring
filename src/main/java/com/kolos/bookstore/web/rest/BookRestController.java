package com.kolos.bookstore.web.rest;


import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.exception.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDto get(@PathVariable Long id) {
        return bookService.get(id);
    }

    @GetMapping
    public Page<BookDto> getAll(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/search_title")
    public Page<BookDto> getByTitle(@RequestParam String title, Pageable pageable) {
        return bookService.getSearchBooks(title, pageable);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody @Valid BookDto bookDto, BindingResult errors) {
        checkErrors(errors);
        BookDto created = bookService.create(bookDto);
        return buildResponseCreated(created);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody @Valid BookDto bookDto, BindingResult errors) {
        checkErrors(errors);
        bookDto.setId(id);
        return bookService.update(bookDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }


    private ResponseEntity<BookDto> buildResponseCreated(BookDto created) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(getLocation(created))
                .body(created);
    }

    private URI getLocation(BookDto bookDto) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/books/{id}")
                .buildAndExpand(bookDto.getId())
                .toUri();
    }

    private void checkErrors(BindingResult errors) {
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }

}

