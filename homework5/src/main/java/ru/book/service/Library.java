package ru.book.service;

import ru.book.domain.Author;
import ru.book.domain.Book;
import ru.book.domain.Genre;

import java.util.List;

public interface Library {
    List<Genre> findGenres(String name);
    List<Author> findAuthors(String name);
    List<Book> findBooksByName(String name);
    List<Book> findBooksByGenre(String name);
    List<Book> findBooksByAuthor(String name);

    Genre getGenre(int id);
    Author getAuthor(int id);

    void insertGenre(Genre genre);
    void insertAuthor(Author author);
    void insertBook(Book book);

    void updateGenre(Genre genre);
    void updateAuthor(Author author);
    void updateBook(Book book);

    void deleteGenre(int id);
    void deleteAuthor(int id);
    void deleteBook(int id);
}
