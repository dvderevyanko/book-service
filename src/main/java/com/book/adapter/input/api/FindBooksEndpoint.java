package com.book.adapter.input.api;

import com.book.adapter.input.dto.BookPageResponseDto;
import com.book.adapter.input.mapper.BookApiMapper;
import com.book.application.port.input.FindBooksIPort;
import com.book.domain.common.Pagination;
import com.book.domain.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FindBooksEndpoint {

    private final static String PAGE_NUMBER = "0";
    private final static String PAGE_SIZE = "100";

    private final BookApiMapper mapper;
    private final FindBooksIPort findBooksUseCase;

    @GetMapping(ApiConstants.BOOK_API_PATH)
    public BookPageResponseDto getBooks(@RequestParam(defaultValue = PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = PAGE_SIZE) Integer size, @RequestParam(required = false) String title) {
        log.info("Received get request for books with title : {}", title);
        Pagination<Book> result = findBooksUseCase.handle(new Pagination<>(page, size, title));
        return mapper.toBookPage(result);
    }
}
