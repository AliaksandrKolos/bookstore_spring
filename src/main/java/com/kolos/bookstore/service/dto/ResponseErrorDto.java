package com.kolos.bookstore.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Data
public class ResponseErrorDto {

    private String message;
    private List<ValidationErrorDto> errors = new ArrayList<>();

    public ResponseErrorDto(String message) {
        this.message = message;
    }

    public void addError(String field, String errorMessage) {
        this.errors.add(new ValidationErrorDto(field, errorMessage));
    }
}
