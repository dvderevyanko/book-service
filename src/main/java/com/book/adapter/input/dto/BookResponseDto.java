package com.book.adapter.input.dto;

import lombok.Data;

@Data
public class BookResponseDto {

    private Long id;
    private String title;
    private AuthorDto author;
    private String type;
    private Integer averageRate;

}
