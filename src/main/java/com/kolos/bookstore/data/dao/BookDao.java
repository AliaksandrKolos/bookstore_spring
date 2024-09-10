package com.kolos.bookstore.data.dao;

import com.kolos.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao extends AbstractDao<Long, BookDto> {

    BookDto findByIsbn(String isbn);

    List<BookDto> findByAuthor(String author, int offset, int limit);

    int countAll();

    List<BookDto> findAllSearch(String searchMessage, int offset, int limit);

    int countAllSearch(String messagesSearch);
}
