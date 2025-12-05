package com.giordanni.libraryapi.controller.common;

import com.giordanni.libraryapi.dtos.errors.FieldErrorResponse;
import com.giordanni.libraryapi.exceptions.InvalidFieldException;
import com.giordanni.libraryapi.exceptions.OperationNotPermittedException;
import com.giordanni.libraryapi.exceptions.RegisterDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import com.giordanni.libraryapi.dtos.errors.ResponseError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleMethodArgumentNotValidException( MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<FieldErrorResponse> listErrors = fieldErrors
                .stream()
                .map(fe -> new FieldErrorResponse(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", listErrors );
    }

    @ExceptionHandler(RegisterDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleRegisterDuplicateException(RegisterDuplicateException e){
        return ResponseError.conflictResponse(e.getMessage());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotPermittedException(OperationNotPermittedException e){
        return ResponseError.standardResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidFieldException(InvalidFieldException e) {
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", List.of(new FieldErrorResponse(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handlerAccessDeniedException(AccessDeniedException e){
        return new ResponseError(HttpStatus.FORBIDDEN.value(), "Access Denied." , List.of());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleErrors(RuntimeException e){
        return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error: " + e.getMessage(), List.of());
    }
}
