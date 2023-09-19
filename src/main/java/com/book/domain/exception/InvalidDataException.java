package com.book.domain.exception;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InvalidDataException extends RuntimeException {
    private final List<String> fields;

    @Override
    public String getMessage() {
        return "Invalid fields : " + String.join(",", fields);
    }
}
