package com.kolos.bookstore.data.entity.converters;

import com.kolos.bookstore.data.entity.Book;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CoverConverter implements AttributeConverter<Book.Cover, Integer> {


    @Override
    public Integer convertToDatabaseColumn(Book.Cover cover) {

        if (cover == null) {
            return null;
        }

        return switch (cover) {
            case PAPERBACK -> 1;
            case HARDCOVER -> 2;
            case LEATHER -> 3;
            case DUST_JACKET -> 4;
        };
    }

    @Override
    public Book.Cover convertToEntityAttribute(Integer integer) {

        if (integer == null) {
            return null;
        }

        return switch (integer) {
            case 1 -> Book.Cover.PAPERBACK;
            case 2 -> Book.Cover.HARDCOVER;
            case 3 -> Book.Cover.LEATHER;
            case 4 -> Book.Cover.DUST_JACKET;
            default -> throw new IllegalArgumentException("Unknown cover id " + integer);
        };
    }
}
