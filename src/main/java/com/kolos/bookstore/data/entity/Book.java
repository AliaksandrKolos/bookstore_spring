package com.kolos.bookstore.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String author;
    private String isbn;
    private String title;
    private String genre;
    private Integer year;
    private Integer pages;
    private BigDecimal price;
    private Cover cover;

    public enum Cover {
        PAPERBACK,
        HARDCOVER,
        LEATHER,
        DUST_JACKET
    }
}
