package com.kolos.bookstore.service;

import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.dto.BookDto;

import java.util.List;


public interface BookService {

    BookDto get(Long id);

    List<BookDto> getAll(PageableDto pageableDto);

    List<BookDto> getAllByAuthor(String author, PageableDto pageableDto);

    BookDto create(BookDto dto);

    BookDto update(BookDto dto);

    void delete(Long id);

    List<BookDto> getSearchBooks(String searchMessage, PageableDto pageableDto);
}
