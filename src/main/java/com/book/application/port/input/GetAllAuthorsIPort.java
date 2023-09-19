package com.book.application.port.input;

import com.book.domain.model.Author;

import java.util.List;

public interface GetAllAuthorsIPort {

    List<Author> handle();
}
