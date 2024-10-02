package com.kolos.bookstore.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Getter
public class ValidationException extends AppException {

    private final BindingResult errors;
}
