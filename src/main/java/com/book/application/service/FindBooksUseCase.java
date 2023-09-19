package com.book.application.service;

import com.book.application.port.input.FindBooksIPort;
import com.book.application.port.output.BookRepositoryOPort;
import com.book.domain.common.Pagination;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBooksUseCase implements FindBooksIPort {

    private static final String SORT_FIELD = "averageRate";

    private final BookRepositoryOPort repository;

    @Override
    public Pagination<Book> handle(Pagination<Book> pagination) {
        pagination.setSortBy(SORT_FIELD);
        pagination.setSortDirection(Sort.Direction.DESC);
        if (pagination.getFieldContaining() != null && !pagination.getFieldContaining().isEmpty()) {
            return repository.findAllBooksByContainingTitle(pagination);
        } else {
            return repository.findAllBooks(pagination);
        }
    }
}
