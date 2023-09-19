package com.book.adapter.output.repository;

import com.book.adapter.output.entity.BookEntity;
import com.book.adapter.output.mapper.BookEntityMapper;
import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.common.Pagination;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostgresBookRepository implements BookRepositoryOPort {

    private final SpringPostgresBookRepository repository;
    private final BookEntityMapper mapper;

    private static PageRequest toPageRequest(Pagination<Book> pagination) {
        return Optional.ofNullable(pagination.getSortBy())
                .map(sortByField -> Sort.by(pagination.getSortDirection(), sortByField))
                .map(sortBy -> PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sortBy))
                .orElse(PageRequest.of(pagination.getPageNumber(), pagination.getPageSize()));
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        return repository.findByTitle(title)
                .map(mapper::toDomain);
    }

    @Override
    public Book saveBook(Book book) {
        Long id = repository.save(mapper.toEntity(book)).getId();
        return getBookById(id).get();
    }

    @Override
    public Pagination<Book> findAllBooks(Pagination<Book> pagination) {
        Page<BookEntity> result = repository.findAll(toPageRequest(pagination));
        setPaginationResult(pagination, result);
        return pagination;
    }

    @Override
    public Pagination<Book> findAllBooksByContainingTitle(Pagination<Book> pagination) {
        Page<BookEntity> result = repository.findByTitleContaining(pagination.getFieldContaining(), toPageRequest(pagination));
        setPaginationResult(pagination, result);
        return pagination;
    }

    private void setPaginationResult(Pagination<Book> pagination, Page<BookEntity> result) {
        pagination.setContent(mapper.toDomains(result.getContent()));
        pagination.setTotalPages(result.getTotalPages());
        pagination.setTotalElements(result.getTotalElements());
    }
}


