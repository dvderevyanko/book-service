package com.book.application.port.input;

import com.book.domain.model.Book;

public interface RateBookIPort {

    Book handle(Long bookId, Integer rate);

}
