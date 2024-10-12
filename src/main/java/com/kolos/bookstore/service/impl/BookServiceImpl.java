package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.repository.BookRepository;
import com.kolos.bookstore.service.BookMapper;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.exception.AppException;
import com.kolos.bookstore.service.exception.NotFoundException;
import com.kolos.bookstore.service.exception.UpdateFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final MessageSource messageSource;
    private final BookMapper bookMapper;


    @Override
    public BookDto get(Long id) {
        Book book = bookRepository.findById(id).
                orElseThrow(() -> new NotFoundException(messageSource.getMessage("book.not_found", new Object[0], LocaleContextHolder.getLocale())));
        return bookMapper.toDto(book);
    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::toDtoShort);
    }

    @Override
    public Page<BookDto> getAllByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthor(author, pageable)
                .map(bookMapper::toDto);
    }

    @Transactional
    @Override
    public BookDto create(BookDto dto) {
        String byIsbnSaved = dto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(byIsbnSaved).orElse(null);
        if (byIsbn != null) {
            throw new AppException(messageSource.getMessage("book.already_exists", new Object[0], LocaleContextHolder.getLocale()));
        }
        Book book = bookMapper.toEntity(dto);
        Book created = bookRepository.save(book);
        return bookMapper.toDto(created);
    }

    @Transactional
    @Override
    public BookDto update(BookDto dto) {
        String isbnSaved = dto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(isbnSaved).orElse(null);
        if (byIsbn != null && !byIsbn.getId().equals(dto.getId())) {
            throw new UpdateFailedException(messageSource.getMessage("book.alreadyExists", new Object[0], LocaleContextHolder.getLocale()));
        }
        Book book = bookMapper.toEntity(dto);
        Book updated = bookRepository.save(book);
        return bookMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        boolean deleted = bookRepository.existsById(id);
        if (!deleted) {
            throw new NotFoundException(messageSource.getMessage("book.not_found_delete", new Object[0], LocaleContextHolder.getLocale()));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> getSearchBooks(String searchMessage, Pageable pageable) {
        return bookRepository.findAllByTitle(searchMessage, pageable)
                .map(bookMapper::toDto);

    }
}
