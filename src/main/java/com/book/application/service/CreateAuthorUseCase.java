package com.book.application.service;

import com.book.application.port.input.CreateAuthorIPort;
import com.book.application.port.output.AuthorRepositoryOPort;
import com.book.domain.exception.DuplicateException;
import com.book.domain.exception.InvalidDataException;
import com.book.domain.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CreateAuthorUseCase implements CreateAuthorIPort {

    private final static int FIELD_LENGTH = 50;
    private final static Map<String, Predicate<Author>> AUTHOR_FIELD_CHECKS = Map.of(
            "name", author -> author.getName() == null || author.getName().isEmpty() || author.getName().length() > FIELD_LENGTH
    );

    private final AuthorRepositoryOPort repository;

    @Override
    public Author handle(Author author) {
        validateAuthor(author);
        return repository.saveAuthor(author);
    }

    private void validateAuthor(Author author) {
        List<String> invalidFields = new ArrayList<>(
                AUTHOR_FIELD_CHECKS.entrySet().stream()
                        .filter(e -> e.getValue().test(author))
                        .map(Map.Entry::getKey)
                        .toList()
        );
        if (!invalidFields.isEmpty()) {
            throw new InvalidDataException(invalidFields);
        } else {
            repository.getAuthorByName(author.getName()).ifPresent(v -> {
                throw new DuplicateException();
            });
        }
    }

}
