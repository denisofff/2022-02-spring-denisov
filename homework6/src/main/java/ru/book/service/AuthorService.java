package ru.book.service;

import ru.book.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAuthors(String name);

    Author getAuthor(int id);

    void insertAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(int id);
}
