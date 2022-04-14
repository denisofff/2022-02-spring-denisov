package ru.book.repository;

import ru.book.domain.Book;

import java.util.List;

public interface BookRepository {
    void insert(Book genre);
    void update(Book genre);
    void delete(int id);

    Book get(int id);

    List<Book> selectAll();

    List<Book> selectByName(String name);
    List<Book> selectByGenre(String name);
    List<Book> selectByAuthor(String name);
}
