package com.giordanni.libraryapi.exceptions;

import lombok.Getter;
import lombok.Setter;

public class InvalidFieldException  extends RuntimeException{

    @Getter
    private String field;

    public InvalidFieldException(String field, String message) {
        super(message);
        this.field = field;
    }
}
