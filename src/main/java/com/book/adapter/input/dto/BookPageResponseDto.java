package com.book.adapter.input.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookPageResponseDto {

    private List<BookResponseDto> content;
    private long totalElements;
    private int totalPages;
    private int page;

}
