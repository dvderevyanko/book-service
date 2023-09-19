package com.book.application.port.input;

import com.book.domain.common.Pagination;
import com.book.domain.model.Book;

public interface FindBooksIPort {

    Pagination<Book> handle(Pagination<Book> pagination);
}
