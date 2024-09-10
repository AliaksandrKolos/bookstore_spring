package com.kolos.bookstore.service.exception;

public class NotFoundException extends AppException {

    public NotFoundException(String message, Long id, Throwable cause) {
        super(message + " " + id, cause);
    }

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
