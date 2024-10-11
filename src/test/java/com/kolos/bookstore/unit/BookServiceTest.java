package com.kolos.bookstore.unit;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.repository.BookRepository;
import com.kolos.bookstore.service.BookMapper;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.exception.AppException;
import com.kolos.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private static final Long ID = 1L;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getBookById() {
        Book book = new Book();
        book.setId(ID);

        BookDto expectedResult = new BookDto();
        expectedResult.setId(ID);

        Mockito.doReturn(Optional.of(book))
                .when(bookRepository).findById(ID);
        Mockito.doReturn(expectedResult)
                .when(bookMapper).toDto(book);

        var actualResult = bookService.get(ID);

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void allBooks() {
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        List<Book> books = List.of(book1, book2);
        Page<Book> bookPage = new PageImpl<>(books);

        BookDto bookDto1 = new BookDto();
        bookDto1.setId(1L);
        bookDto1.setTitle("Book 1");

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setTitle("Book 2");

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDtoShort(book1)).thenReturn(bookDto1);
        when(bookMapper.toDtoShort(book2)).thenReturn(bookDto2);

        Pageable pageable = PageRequest.of(0, 2);

        Page<BookDto> result = bookService.getAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Book 1", result.getContent().get(0).getTitle());
        assertEquals("Book 2", result.getContent().get(1).getTitle());

        verify(bookRepository, times(1)).findAll(pageable);
        verify(bookMapper, times(1)).toDtoShort(book1);
        verify(bookMapper, times(1)).toDtoShort(book2);
    }

    @Test
    void createBook_whenBookDoesNotExist_shouldCreateBook() {

        BookDto bookDto = new BookDto();
        bookDto.setIsbn("1234567890");
        bookDto.setTitle("Title");

        Book book = new Book();
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());

        Book createBook = new Book();
        createBook.setId(1L);
        createBook.setIsbn(bookDto.getIsbn());
        createBook.setTitle(bookDto.getTitle());

        when(bookRepository.findByIsbn(bookDto.getIsbn())).thenReturn(Optional.empty());
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(createBook);
        when(bookMapper.toDto(createBook)).thenReturn(bookDto);

        BookDto result = bookService.create(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getIsbn(), result.getIsbn());
        assertEquals(bookDto.getTitle(), result.getTitle());

        verify(bookRepository, times(1)).findByIsbn(bookDto.getIsbn());
        verify(bookMapper, times(1)).toEntity(bookDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(createBook);
    }

    @Test
    void createBook_whenBookAlreadyExists_shouldThrowException() {
        BookDto bookDto = new BookDto();
        bookDto.setIsbn("1234567");

        Book existingBook = new Book();
        existingBook.setIsbn(bookDto.getIsbn());

        when(bookRepository.findByIsbn(bookDto.getIsbn())).thenReturn(Optional.of(existingBook));
        when(messageSource.getMessage("book.already_exists", new Object[0], LocaleContextHolder.getLocale()))
                .thenReturn("book already exists");

        Exception exception = assertThrows(AppException.class, () -> {
            bookService.create(bookDto);
        });

        assertEquals("book already exists", exception.getMessage());

        verify(bookRepository, times(1)).findByIsbn(bookDto.getIsbn());
        verify(bookMapper, never()).toEntity(bookDto);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void update_shouldReturnUpdatedBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setIsbn("1234567890");

        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());

        when(bookRepository.findByIsbn(bookDto.getIsbn())).thenReturn(Optional.empty());
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto updatedBookDto = bookService.update(bookDto);

        assertNotNull(updatedBookDto);
        assertEquals(bookDto.getId(), updatedBookDto.getId());
        verify(bookRepository, times(1)).findByIsbn(bookDto.getIsbn());
        verify(bookMapper, times(1)).toEntity(bookDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).toDto(book);
    }
}