package com.book.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@AllArgsConstructor
@Data
public class Pagination<T> {

    private int pageNumber;
    private int pageSize;
    private String fieldContaining;
    private String sortBy;
    private Sort.Direction sortDirection;
    private int totalPages;
    private long totalElements;
    private List<T> content;


    public Pagination(int pageNumber, int pageSize, String fieldContaining) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.fieldContaining = fieldContaining;
    }
}
