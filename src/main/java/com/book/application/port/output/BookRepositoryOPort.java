package com.book.application.port.output;

import com.book.domain.common.Pagination;
import com.book.domain.model.Book;

import java.util.Optional;

public interface BookRepositoryOPort {

    Optional<Book> getBookById(Long id);

    Optional<Book> getBookByTitle(String title);

    Book saveBook(Book book);

    Pagination<Book> findAllBooks(Pagination<Book> pagination);

    Pagination<Book> findAllBooksByContainingTitle(Pagination<Book> pagination);

}
