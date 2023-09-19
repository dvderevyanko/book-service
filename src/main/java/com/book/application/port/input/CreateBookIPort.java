package com.book.application.port.input;

import com.book.domain.model.Book;

public interface CreateBookIPort {

    Book handle(Book newBook, Integer rate);
}
