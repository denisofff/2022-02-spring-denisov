package ru.book.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Book {
    private final Integer id;
    private final String name;

    private final Genre genre;
    private final Author author;
}
