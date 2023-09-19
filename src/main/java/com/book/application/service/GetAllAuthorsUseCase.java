package com.book.application.service;

import com.book.application.port.input.GetAllAuthorsIPort;
import com.book.application.port.output.AuthorRepositoryOPort;
import com.book.domain.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllAuthorsUseCase implements GetAllAuthorsIPort {

    private final AuthorRepositoryOPort repository;

    @Override
    public List<Author> handle() {
        return repository.findAllAuthors();
    }

}
