package com.book.adapter.output.repository;

import com.book.adapter.output.mapper.AuthorEntityMapper;
import com.book.application.port.output.AuthorRepositoryOPort;
import com.book.domain.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgresAuthorRepository implements AuthorRepositoryOPort {

    private final SpringPostgresAuthorRepository repository;
    private final AuthorEntityMapper mapper;

    @Override
    public Author saveAuthor(Author author) {
        return mapper.toDomain(repository.save(mapper.toEntity(author)));
    }

    @Override
    public List<Author> findAllAuthors() {
        return mapper.toDomains(repository.findAll());
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        return repository.findAuthorEntityByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
