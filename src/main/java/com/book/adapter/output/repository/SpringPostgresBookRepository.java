package com.book.adapter.output.repository;

import com.book.adapter.output.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpringPostgresBookRepository extends CrudRepository<BookEntity, Long> {

    Page<BookEntity> findAll(Pageable pageable);

    Page<BookEntity> findByTitleContaining(String title, Pageable pageable);

    Optional<BookEntity> findByTitle(String title);


}
