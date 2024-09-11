package com.kolos.bookstore.data.repository.impl;

import com.kolos.bookstore.data.dao.BookDao;
import com.kolos.bookstore.data.dto.BookDto;
import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.data.mapper.DataMapper;
import com.kolos.bookstore.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookDao bookDao;
    private final DataMapper dataMapper;


    @Override
    public Book find(Long id) {
        BookDto bookDto = bookDao.find(id);
        return dataMapper.toEntity(bookDto);
    }

    @Override
    public Book save(Book entity) {
        BookDto bookDto = dataMapper.toDto(entity);
        BookDto saved = bookDao.save(bookDto);
        return dataMapper.toEntity(saved);
    }

    @Override
    public Book update(Book entity) {
        BookDto bookDto = dataMapper.toDto(entity);
        bookDto.setId(entity.getId());
        BookDto updated = bookDao.update(bookDto);
        return dataMapper.toEntity(updated);
    }

    @Override
    public Book findByIsbn(String isbn) {
        BookDto byIsbn = bookDao.findByIsbn(isbn);
        if (byIsbn == null) {
            return null;
        }
        return dataMapper.toEntity(byIsbn);
    }

    @Override
    public List<Book> findByAuthor(String author, int limit, int offset) {
        return bookDao.findByAuthor(author, limit, offset)
                .stream()
                .map(dataMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public int countAll() {
        return bookDao.countAll();
    }


    @Override
    public int countAll(String messages) {
        return bookDao.countAllSearch(messages);
    }

    @Override
    public List<Book> findAllSearch(String searchMessage, int limit, int offset) {
        return bookDao.findAllSearch(searchMessage, offset, limit)
                .stream()
                .map(dataMapper::toShortEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll(int limit, int offset) {
        return bookDao.findAll(limit, offset)
                .stream()
                .map(dataMapper::toShortEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.delete(id);
    }
}
