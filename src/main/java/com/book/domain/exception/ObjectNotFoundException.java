package com.book.domain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectNotFoundException extends RuntimeException {

    private final String objectName;

    @Override
    public String getMessage() {
        return String.format("Object %s not found", objectName);
    }
}
