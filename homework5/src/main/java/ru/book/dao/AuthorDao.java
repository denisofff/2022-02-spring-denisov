package ru.book.dao;

import ru.book.domain.Author;

import java.util.List;

public interface AuthorDao {
    void insert(Author author);
    void update(Author author);
    void delete(int id);

    Author get(int id);
    List<Author> selectAll();
    List<Author> selectByName(String name);
}
