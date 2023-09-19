package com.book.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BookType {
    MYSTERY("Mystery"), FANTASY("Fantasy"), HORROR("Horror");

    private String name;

    public static BookType findTypeByName(String name) {
        return Arrays.stream(BookType.values()).filter(t -> t.getName().equals(name)).findFirst().orElse(null);
    }

}
