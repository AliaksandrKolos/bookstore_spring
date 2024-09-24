package com.kolos.bookstore.service;

import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    BookDto get(Long id);

    Page<BookDto> getAll(Pageable pageable);

    Page<BookDto> getAllByAuthor(String author, Pageable pageable);

    BookDto create(BookDto dto);

    BookDto update(BookDto dto);

    void delete(Long id);

    Page<BookDto> getSearchBooks(String searchMessage, Pageable pageable);
}
