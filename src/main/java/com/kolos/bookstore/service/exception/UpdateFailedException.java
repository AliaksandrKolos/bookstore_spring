package com.kolos.bookstore.service.exception;

public class UpdateFailedException extends AppException {

    public UpdateFailedException() {
        super();
    }

    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateFailedException(Throwable cause) {
        super(cause);
    }
}
