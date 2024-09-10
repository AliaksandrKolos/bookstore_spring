package com.kolos.bookstore.service.exception;

public class InvalidOrderStatusTransitionException extends AppException {
    public InvalidOrderStatusTransitionException() {
        super();
    }

    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }

    public InvalidOrderStatusTransitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOrderStatusTransitionException(Throwable cause) {
        super(cause);
    }
}
