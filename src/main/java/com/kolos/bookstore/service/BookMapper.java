package com.kolos.bookstore.service;

import com.kolos.bookstore.data.entity.Book;
import com.kolos.bookstore.service.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book entity);

    Book toEntity(BookDto dto);

    BookDto toDtoShort(Book entity);
}
