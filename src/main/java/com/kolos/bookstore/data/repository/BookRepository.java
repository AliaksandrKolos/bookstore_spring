package com.kolos.bookstore.data.repository;

import com.kolos.bookstore.data.entity.Book;

import java.util.List;

public interface BookRepository extends AbstractRepository<Long, Book> {

    Book findByIsbn(String isbn);

    List<Book> findByAuthor(String author, long limit, long offset);

    long countAll();

    List<Book> findAllByTitle(String searchMessage, long limit, long offset);

    int countAll(String messages);


}
