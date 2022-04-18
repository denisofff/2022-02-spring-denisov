package ru.book.repository;

import ru.book.domain.Genre;

import java.util.List;

public interface GenreRepository {
    void insert(Genre genre);
    void update(Genre genre);
    void delete(int id);

    Genre get(int id);

    List<Genre> selectAll();
    List<Genre> selectByIds(List<Integer> ids);
    List<Genre> selectByName(String name);
}
