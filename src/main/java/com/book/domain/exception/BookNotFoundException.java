package com.book.domain.exception;

public class BookNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Book not found";
    }
}
