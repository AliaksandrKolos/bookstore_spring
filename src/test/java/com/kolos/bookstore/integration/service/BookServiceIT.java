package com.kolos.bookstore.integration.service;

import com.kolos.bookstore.annotation.IT;
import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.repository.BookRepository;
import com.kolos.bookstore.service.BookMapper;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.impl.BookServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IT
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceIT {

    private static final Long ID = 1L;

    private final BookServiceImpl bookService;

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    @Test
    void findById() {

        Optional<Book> maybeBook = bookRepository.findById(ID);
        assertNotNull(maybeBook);
        maybeBook.ifPresent(book -> assertEquals("9780061220084", book.getIsbn()));
    }

    @Test
    void create() {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor("Test Author");
        bookDto.setIsbn(String.valueOf(System.currentTimeMillis()));
        bookDto.setTitle("Test Title");
        bookDto.setGenre("Test Genre");
        bookDto.setYear(1234);
        bookDto.setPages(1234);
        bookDto.setPrice(new BigDecimal(1234));
        bookDto.setCover(BookDto.Cover.HARDCOVER);

        BookDto bookActual = bookService.create(bookDto);

        assertNotNull(bookActual);
        assertEquals(bookDto.getAuthor(), bookActual.getAuthor());
        assertEquals(bookDto.getIsbn(), bookActual.getIsbn());
        assertEquals(bookDto.getTitle(), bookActual.getTitle());
        assertEquals(bookDto.getGenre(), bookActual.getGenre());
        assertEquals(bookDto.getYear(), bookActual.getYear());
        assertEquals(bookDto.getPages(), bookActual.getPages());
        assertEquals(bookDto.getPrice(), bookActual.getPrice());
        assertEquals(bookDto.getCover(), bookActual.getCover());
    }

    @Test
    void update() {
        BookDto bookDto = bookService.get(ID);
        String oldIsbn = bookDto.getIsbn();
        bookDto.setIsbn(UUID.randomUUID().toString());

        BookDto bookActual = bookService.update(bookDto);

        assertNotNull(bookActual);
        assertEquals(bookDto.getAuthor(), bookActual.getAuthor());
        assertNotEquals(oldIsbn, bookActual.getIsbn());
        assertEquals(bookDto.getTitle(), bookActual.getTitle());
        assertEquals(bookDto.getGenre(), bookActual.getGenre());
        assertEquals(bookDto.getYear(), bookActual.getYear());
        assertEquals(bookDto.getPages(), bookActual.getPages());
        assertEquals(bookDto.getPrice(), bookActual.getPrice());
        assertEquals(bookDto.getCover(), bookActual.getCover());
    }
}
