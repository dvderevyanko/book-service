package com.book.application.port.input;

import com.book.domain.model.Author;

public interface CreateAuthorIPort {

    Author handle(Author author);
}
