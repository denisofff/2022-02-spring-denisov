package ru.book.service;

import ru.book.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findGenres(String name);

    Genre getGenre(int id);

    void insertGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenre(int id);
}
