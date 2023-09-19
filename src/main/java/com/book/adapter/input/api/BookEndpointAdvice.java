package com.book.adapter.input.api;

import com.book.domain.exception.BookNotFoundException;
import com.book.domain.exception.DuplicateException;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.book.adapter.input")
public class BookEndpointAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ObjectNotFoundException.class})
    public RestError objectNotFoundException(ObjectNotFoundException exception) {
        log.error(exception.getMessage());
        return new RestError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateException.class})
    public RestError duplicateBookException(DuplicateException exception) {
        log.error(exception.getMessage());
        return new RestError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidDataException.class})
    public RestError invalidDataException(InvalidDataException exception) {
        log.error(exception.getMessage());
        return new RestError(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({BookNotFoundException.class})
    public RestError notFoundEception(BookNotFoundException exception) {
        log.error(exception.getMessage());
        return new RestError(exception.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public RestError internalException(Exception exception) {
        log.error(exception.getMessage());
        return new RestError(exception.getMessage());
    }

}