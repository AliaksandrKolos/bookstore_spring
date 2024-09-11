package com.kolos.bookstore.service.impl;

import com.kolos.bookstore.service.util.MassageManager;
import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.repository.BookRepository;
import com.kolos.bookstore.service.BookService;
import com.kolos.bookstore.service.ServiceMapper;
import com.kolos.bookstore.service.dto.BookDto;
import com.kolos.bookstore.service.dto.PageableDto;
import com.kolos.bookstore.service.exception.AppException;
import com.kolos.bookstore.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.kolos.bookstore.service.util.PageUtil.getTotalPages;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public BookDto get(Long id) {
        log.debug("Calling getById {}", id);
        Book book = bookRepository.find(id);
        if (book == null) {
            throw new NotFoundException(MassageManager.INSTANCE.getMessage("book.not_found") + id);
        }
        return serviceMapper.toDto(book);
    }


    @Override
    public List<BookDto> getAll(PageableDto pageableDto) {
        log.debug("Calling getAll");
        List<BookDto> books = bookRepository.findAll(pageableDto.getLimit(), pageableDto.getOffset())
                .stream()
                .map(serviceMapper::toDtoShort).
                toList();
        int count = bookRepository.countAll();
        int pages = getTotalPages(pageableDto, count);
        pageableDto.setTotalItems(count);
        pageableDto.setTotalPages(pages);
        return books;
    }

    @Override
    public List<BookDto> getAllByAuthor(String author, PageableDto pageableDto) {
        List<BookDto> books = bookRepository.findByAuthor(author, pageableDto.getLimit(), pageableDto.getOffset()).stream()
                .map(serviceMapper::toDtoShort)
                .toList();
        int count = bookRepository.countAll(author);
        int pages = getTotalPages(pageableDto, count);
        pageableDto.setTotalItems(count);
        pageableDto.setTotalPages(pages);
        return books;
    }


    @Override
    public BookDto create(BookDto dto) {
        log.debug("Calling create");
        String byIsbnSaved = dto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(byIsbnSaved);
        if (byIsbn != null) {
            throw new AppException(MassageManager.INSTANCE.getMessage("book.already_exists") + byIsbnSaved);
        }
        Book book = serviceMapper.toEntity(dto);
//        TODO validation
        Book created = bookRepository.save(book);
        return serviceMapper.toDto(created);
    }


    @Override
    public BookDto update(BookDto dto) {
        log.debug("Calling update");
        String isbnSaved = dto.getIsbn();
        Book byIsbn = bookRepository.findByIsbn(isbnSaved);
        if (byIsbn != null && !byIsbn.getId().equals(dto.getId())) {
            throw new AppException(MassageManager.INSTANCE.getMessage("book.alreadyExists") + isbnSaved);
        }
        Book book = serviceMapper.toEntity(dto);
//        TODO validation
        Book updated = bookRepository.save(book);
        return serviceMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        log.debug("Calling delete");
        boolean deleted = bookRepository.delete(id);
        if (!deleted) {

            throw new AppException(MassageManager.INSTANCE.getMessage("book.not_found_delete") + id);
        }

    }

    @Override
    public List<BookDto> getSearchBooks(String searchMessage, PageableDto pageableDto) {
        log.debug("Calling getAll");
        List<Book> books = bookRepository.findAllSearch(searchMessage, pageableDto.getLimit(), pageableDto.getOffset());
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(serviceMapper.toDtoShort(book));
        }
        int count = bookRepository.countAll(searchMessage);
        int pages = getTotalPages(pageableDto, count);
        pageableDto.setTotalItems(count);
        pageableDto.setTotalPages(pages);
        return bookDtos;
    }
}
