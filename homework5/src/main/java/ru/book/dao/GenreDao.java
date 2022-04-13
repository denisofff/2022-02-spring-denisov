package ru.book.dao;

import ru.book.domain.Genre;

import java.util.List;

public interface GenreDao {
    void insert(Genre genre);
    void update(Genre genre);
    void delete(int id);

    Genre get(int id);
    List<Genre> selectAll();
    List<Genre> selectByName(String name);
}
