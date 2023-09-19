package com.book.adapter.input.dto;

import lombok.Data;

@Data
public class CreateBookDto {

    private String title;
    private Long authorId;
    private String type;
    private Integer rate;

}
