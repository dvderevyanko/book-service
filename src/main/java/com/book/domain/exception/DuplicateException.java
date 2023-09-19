package com.book.domain.exception;

public class DuplicateException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Object already exists";
    }
}
