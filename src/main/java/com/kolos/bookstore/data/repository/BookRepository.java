package com.kolos.bookstore.data.repository;

import com.kolos.bookstore.data.entity.Book;

import java.util.List;

public interface BookRepository extends AbstractRepository<Long, Book> {

    Book findByIsbn(String isbn);

    List<Book> findByAuthor(String author, int limit, int offset);

    int countAll();

    List<Book> findAllSearch(String searchMessage, int limit, int offset);

    int countAll(String messages);


}
