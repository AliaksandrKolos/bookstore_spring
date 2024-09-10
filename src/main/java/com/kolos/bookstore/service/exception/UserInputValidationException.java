package com.kolos.bookstore.service.exception;

import java.util.List;

public class UserInputValidationException extends AppException {

    private  List<String> errorMessages;

    public UserInputValidationException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public UserInputValidationException() {
        super();
    }

    public UserInputValidationException(String message) {
        super(message);
    }

    public UserInputValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInputValidationException(Throwable cause) {
        super(cause);
    }
}
