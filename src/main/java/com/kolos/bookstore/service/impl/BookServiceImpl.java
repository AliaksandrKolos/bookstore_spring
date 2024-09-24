package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.repository.BookRepository;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.ServiceMapper;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.exception.AppException;
import com.kolos.bookstore.service.exception.NotFoundException;
import com.kolos.bookstore.service.exception.UpdateFailedException;
import com.kolos.bookstore.service.util.MessageManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ServiceMapper serviceMapper;
    private final MessageManager messageManager;

    @Override
    public BookDto get(Long id) {
        log.debug("Calling getById {}", id);
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new NotFoundException(messageManager.getMessage("book.not_found") + id);
        }
        return serviceMapper.toDto(book);
    }


    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        log.debug("Calling getAll");
        return bookRepository.findAll(pageable).map(serviceMapper::toDtoShort);

    }

    @Override
    public Page<BookDto> getAllByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthor(author, pageable).map(serviceMapper::toDto);
    }

    @Transactional
    @Override
    public BookDto create(BookDto dto) {
        String byIsbnSaved = dto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(byIsbnSaved).orElse(null);
        if (byIsbn != null) {
            throw new AppException(messageManager.getMessage("book.already_exists") + byIsbnSaved);
        }
        Book book = serviceMapper.toEntity(dto);
        Book created = bookRepository.save(book);
        return serviceMapper.toDto(created);
    }

    @Transactional
    @Override
    public BookDto update(BookDto dto) {
        log.debug("Calling update");
        String isbnSaved = dto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(isbnSaved).orElse(null);
        if (byIsbn != null && !byIsbn.getId().equals(dto.getId())) {
            throw new UpdateFailedException(messageManager.getMessage("book.alreadyExists") + isbnSaved);
        }
        Book book = serviceMapper.toEntity(dto);
        Book updated = bookRepository.save(book);
        return serviceMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.debug("Calling delete");
        boolean deleted = bookRepository.existsById(id);
        if (!deleted) {
            throw new NotFoundException(messageManager.getMessage("book.not_found_delete") + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookDto> getSearchBooks(String searchMessage, Pageable pageable) {
        log.debug("Calling getAll");
        return bookRepository.findAllByTitle(searchMessage, pageable).map(serviceMapper::toDto);

    }
}
