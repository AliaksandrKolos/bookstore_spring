package com.kolos.bookstore.data.repository;

import com.kolos.bookstore.data.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("FROM Book b WHERE b.isbn = :isbn AND b.deleted = false")
    Optional<Book> findByIsbn(String isbn);

    @Query("FROM Book b where b.author = :author and b.deleted = false")
    Page<Book> findByAuthor(String author, Pageable pageable);

    @Query("from Book b where b.title like %:searchMessage% and b.deleted = false")
    Page<Book> findAllByTitle(String searchMessage, Pageable pageable);

    @Override
    @Query("FROM Book b WHERE b.deleted = false")
    Page<Book> findAll(Pageable pageable);

    @Override
    @Query("from Book b where b.id = :id and b.deleted = false")
    Optional<Book> findById(Long id);

    @Modifying
    @Query("UPDATE Book b SET b.deleted = true WHERE b.id = :id")
    void deleteById(Long id);
}

