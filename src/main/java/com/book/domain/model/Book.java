package com.book.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Book {

    private Long id;
    private String title;
    private Author author;
    private BookType type;
    private Integer totalRate;
    private Integer rateCount;
    private Integer averageRate;
}
