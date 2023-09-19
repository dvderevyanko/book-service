package com.book.application.port.output;

import com.book.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryOPort {

    Author saveAuthor(Author author);

    List<Author> findAllAuthors();

    Optional<Author> getAuthorByName(String name);

    Optional<Author> getAuthorById(Long id);

}
