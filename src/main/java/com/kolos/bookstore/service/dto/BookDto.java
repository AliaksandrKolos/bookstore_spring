package com.kolos.bookstore.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {

    private Long id;
    private String author;
    @NotBlank(message = "Field cannot be empty")
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

