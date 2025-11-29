package com.giordanni.libraryapi.exceptions;

public class OperationNorPermittedException extends RuntimeException{

    public OperationNorPermittedException(String message) {
        super(message);
    }
}
