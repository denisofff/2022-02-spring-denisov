package ru.book.repository;

import ru.book.domain.Author;

import java.util.List;

public interface AuthorRepository {
    void insert(Author author);
    void update(Author author);
    void delete(int id);

    Author get(int id);
    List<Author> selectAll();
    List<Author> selectByIds(String ids);
    List<Author> selectByName(String name);
}
