package com.kolos.bookstore.web.errorHandler;

import com.kolos.bookstore.service.dto.ResponseErrorDto;
import com.kolos.bookstore.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice("com.kolos.bookstore.web.rest")
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    private static final String SERVER_ERROR = "Server Error";


    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleException(ValidationException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto("Validation error");
        List<FieldError> fieldErrors = e.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            responseErrorDto.addError(field, message);
        }

        return new ResponseEntity<>(responseErrorDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto("Access denied");
        return new ResponseEntity<>(responseErrorDto, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleException(NotFoundException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(e.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleException(UpdateFailedException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(e.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleException(InvalidOrderStatusTransitionException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(e.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler
//    public ResponseEntity<ResponseErrorDto> handleException(Exception e) {
//        ResponseErrorDto responseErrorDto = new ResponseErrorDto(SERVER_ERROR);
//        log.info("Server Error", e);
//        return new ResponseEntity<>(responseErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler
    public ResponseEntity<ResponseErrorDto> handleException(UserInputValidationException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(e.getMessage());
        return new ResponseEntity<>(responseErrorDto, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = String.format("Invalid parameter: expected type '%s' but received value '%s'", Objects.requireNonNull(ex.getRequiredType()).getSimpleName(), ex.getValue());
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(message);
        return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
    }
}
