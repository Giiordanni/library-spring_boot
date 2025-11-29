package com.giordanni.libraryapi.exceptions;

public class RegisterDuplicateException extends RuntimeException {
    public RegisterDuplicateException(String message) {
        super(message);
    }
}
