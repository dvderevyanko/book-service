package com.book.adapter.output.repository;

import com.book.adapter.output.entity.AuthorEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringPostgresAuthorRepository extends CrudRepository<AuthorEntity, Long> {

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findAuthorEntityByName(String name);
}
